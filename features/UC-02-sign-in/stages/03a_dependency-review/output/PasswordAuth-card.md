# Dependency review — `PasswordAuth`

## Section 1 — Invocations received

| Action | Flow (sync) | Data received | Pattern | Source |
|---|---|---|---|---|
| `check` | `WhenUserLookupByEmailFoundThenPasswordAuthCheckForSignIn` (sign-in) | userId, password | A + D | `userId` from trigger token, `password` from Web request input |

## Section 2 — Named-region reads by others (inbound Pattern D)

None — no other concept's sync reads `PasswordAuth` named region.

## Route-filter analysis

`PasswordAuth.check` is only invoked for UC-02 sign-in (and potentially UC-03 password change). No route filter needed for sign-in flow.

## Inconsistencies and risks

- None.
