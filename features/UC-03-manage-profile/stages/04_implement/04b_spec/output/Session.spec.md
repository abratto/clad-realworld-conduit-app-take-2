# Session — SPEC

## Actions

### `grant(userId: UserId) -> Granted`
- **Inputs:** `userId: UserId`
- **Outcomes (enum):** `Granted`
- **Flow token:** `Session.grant { userId, sessionId, outcome }`

### `lookup(token: String) -> FOUND | error:unknown`
- **Inputs:** `token: String`
- **Outcomes (enum):** `FOUND`, `error:unknown`
- **Flow token:** `Session.lookup { token, outcome }`
