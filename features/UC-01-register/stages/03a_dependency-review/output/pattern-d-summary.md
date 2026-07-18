# Pattern D summary — UC-01-register

## Pattern D reads

| Consumer (sync) | Field read | Owner concept | Key | In flow |
|---|---|---|---|---|
| `WhenUserRegisterRefusedByDuplicateUsernameThenWebRespondForRegister` | `username` | `User` | `?username` (attempted) | `register-account` |
| `WhenUserRegisterRefusedByDuplicateEmailThenWebRespondForRegister` | `email` | `User` | `?email` (attempted) | `register-account` |
| `WhenSessionGrantGrantedThenWebRespondForRegister` | `username` | `User` | `?userId` (from `Session.grant` outcome) | `register-account` |
| `WhenSessionGrantGrantedThenWebRespondForRegister` | `email` | `User` | `?userId` (from `Session.grant` outcome) | `register-account` |

## Cross-flow inconsistencies

- No cross-flow inconsistencies — all Pattern D reads are within a single flow (`register-account`).
- The `User.username` and `User.email` fields are read via Pattern D with two different keys:
  - By the 409 syncs: keyed by the attempted value (`?username`, `?email`)
  - By the 201 sync: keyed by `?userId` (opaque identifier from the `Session.grant` outcome)
  This is consistent because the different syncs have different lookup purposes (conflict detection vs. response assembly).

## What this feeds

- **Stage 03b (data model).** `User` data model must include `username` and `email` fields.
- **Stage 04a (storage mapping).** `User` storage mapping must make `username` and `email` queryable by both value (for conflict checks) and by `userId` (for response assembly).
- **Stage 04b (spec).** Consumer syncs must name `User` concept and fields.
- **Stage 05 (verify).** Each row is a runtime trace target.
