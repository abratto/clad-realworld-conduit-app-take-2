Resume point: next feature is `UC-02-sign-in` — start at `features/UC-02-sign-in/stages/01_usecase/CONTEXT.md`

# Verification trace — UC-01-register

## Scenario: register-account

**Gherkin scenario:** `Register a new user successfully` (register-account.feature:12)
**Use-case:** `register-account` (usecase.md)

### Runtime evidence

Verified via Cucumber end-to-end test (`CucumberTest`). The flow test starts an embedded Micronaut server, sends a `POST /api/users` request, and asserts the response.

**Token chain (expected, from chain table):**
```
Web/request[POST /api/users] -> Web.handle -> Routed
Web.handle[Routed] -> User.register -> Registered
User.register[Registered] -> Session.grant -> Granted
Session.grant[Granted] -> Web.respond -> 201
```

**Token chain (actual, from Cucumber test passing):**
- `POST /api/users` → WebController creates `Web/request` with route `/api/users` and body fields
- `WhenWebHandleRoutedThenUserRegisterForRegister` sync fires → creates `User.register` invocation
- `UserConcept.pollAll()` processes `User.register` → emits `Registered` outcome with `userId`
- `WhenUserRegisterRegisteredThenSessionGrantForRegister` sync fires → creates `Session.grant` invocation
- `SessionConcept.pollAll()` processes `Session.grant` → emits `Granted` outcome with `sessionToken`
- `WhenSessionGrantGrantedThenWebRespondForRegister` sync fires → creates `Web/respond[201]`
- `SyncDispatcher.checkForResponse()` detects `Web/respond` → returns 201 with user fields
- `RegisterController` assembles response via `ResponseAssembler` → DTO with username, email, token

**Assertions:**
- Response status: 201 (CREATED)
- Body contains: username, email, token
- No concept state leaked into transport boundary

### Error paths

All verified via Cucumber failure-path scenarios:

| Branch | Expected | Actual | Status |
|---|---|---|---|
| Blank username | 422 | 422 | ✅ |
| Blank email | 422 | 422 | ✅ |
| Blank password | 422 | 422 | ✅ |
| Duplicate username | 409 | 409 | ✅ |
| Duplicate email | 409 | 409 | ✅ |

### Cross-checks

- Every action in the runtime chain is authorised by a sync in `03_syncs/output/` ✅
- The chain follows bootstrap → concept → sync → concept → sync → Web/respond pattern (R4) ✅
- No imperative branching in the controller (RegisterController delegates to FlowManager) ✅
- Gherkin scenario names match use-case scenario names ✅
