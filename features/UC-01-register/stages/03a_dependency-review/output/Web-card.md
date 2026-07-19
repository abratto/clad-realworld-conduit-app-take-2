# Dependency review — `Web`

## Section 1 — Invocations received

| Action | Flow (sync) | Data received | Pattern | Source |
|---|---|---|---|---|
| `respond` | `WhenWebHandleBlankFieldsThenWebRespondForRegister` (`register-account`) | status: 422, body: error envelope | C | sync constants `422`, `{"errors": {<field>: ["can't be blank"]}}` |
| `respond` | `WhenUserRegisterDuplicateUsernameThenWebRespondForRegister` (`register-account`) | status: 409, body: username conflict | D + C | `User` state read (by username), sync constants `409`, `{"errors": {"username": [...]}}` |
| `respond` | `WhenUserRegisterDuplicateEmailThenWebRespondForRegister` (`register-account`) | status: 409, body: email conflict | D + C | `User` state read (by email), sync constants `409`, `{"errors": {"email": [...]}}` |
| `respond` | `WhenSessionGrantGrantedThenWebRespondForRegister` (`register-account`) | status: 201, body: user object with token | A + C + D | `?token` from trigger token, `?status=201` constant, `?username`/`?email` via D from `User` state |

## Section 2 — Named-region reads by others (inbound Pattern D)

None — `Web` has no named state region that other concepts read. It is the bootstrap transport concept.

## Route-filter analysis

| Sync | Trigger | Produced by multiple routes? | Route filter | Justification |
|---|---|---|---|---|
| `WhenWebHandleBlankFieldsThenWebRespondForRegister` | `Web.handle[Refused:blankFields]` | Yes — login and other flows can also produce blank-field refusals | Required — filter on route `POST /api/users` | |
| `WhenUserRegisterDuplicateUsernameThenWebRespondForRegister` | `User.register[refused]` | No — `User.register` is only invoked for UC-01-register | Not needed | Unique trigger action |
| `WhenUserRegisterDuplicateEmailThenWebRespondForRegister` | `User.register[refused]` | No — same action, unique to UC-01-register | Not needed | Unique trigger action |
| `WhenSessionGrantGrantedThenWebRespondForRegister` | `Session.grant[Granted]` | Yes — UC-02-sign-in also produces `Session.grant[Granted]` | Required — filter on route `POST /api/users` | |

## Inconsistencies and risks

- `Web.respond` is called with different pattern mixes across flows (C-only, D+C, A+C+D). This is expected since `respond` is a generic transport action that different syncs supply with different data.
- The `User.register` trigger is unique to UC-01, so syncs firing on its outcomes do not need route filters. Session.grant will be produced by both UC-01 and UC-02, so the registration sync on that trigger needs a route filter.

## Cross-checks

- Every `Action` row exists in the bootstrap concept spec (`methodology/architecture/WEB_CONCEPT.md`).
- Every `Sync` named here exists in `../../03_syncs/output/`.
- No Pattern D rows — Web has no named state region.
- Every copied token matches the approved sync text exactly.
