# Dependency review — `User`

## Section 1 — Invocations received

| Action | Flow (sync) | Data received | Pattern | Source |
|---|---|---|---|---|
| `register` | `WhenWebHandleRoutedThenUserRegisterForRegister` (`register-account`) | username, email, password | A | `Web.handle` body (`routed` token carries `?username`, `?email`, `?password`) |

## Section 2 — Named-region reads by others (inbound Pattern D)

| Field | Read by (sync) | In flow | Pattern | Key |
|---|---|---|---|---|
| `username` | `WhenUserRegisterDuplicateUsernameThenWebRespondForRegister` | `register-account` | D | `?username` from trigger |
| `email` | `WhenUserRegisterDuplicateEmailThenWebRespondForRegister` | `register-account` | D | `?email` from trigger |
| `username` | `WhenSessionGrantGrantedThenWebRespondForRegister` | `register-account` | D | `?userId` from trigger |
| `email` | `WhenSessionGrantGrantedThenWebRespondForRegister` | `register-account` | D | `?userId` from trigger |

## Route-filter analysis

`User.register` is only invoked for UC-01-register. No other use case calls `User.register`. Therefore syncs firing on `User.register` outcomes do not need route filters.

## Inconsistencies and risks

- `username` and `email` are read via Pattern D by two different syncs. Both reads are from the same flow (`register-account`), so no cross-flow inconsistency.
- The `Session.grant` triggered by `User.register[Registered]` does not read User state — it relies on Pattern A for `userId` carried from the `Registered` outcome. The later `Web.respond[201]` sync then reads User state via D to get username/email. This split is clean: the `Session.grant` sync only needs the opaque `userId`, while the response sync needs the display fields.

## Cross-checks

- Every `Action` row exists in `User.concept.md`.
- Every `Sync` named here exists in `../../03_syncs/output/`.
- Every Pattern D `Field` appears in `User` state section.
- Every copied token matches the approved sync text exactly.
