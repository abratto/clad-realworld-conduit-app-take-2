# Session — SPEC

## Actions

### `grant(userId: UserId) -> Granted`

- **Inputs:** `userId: UserId`
- **Outcomes (enum):** `Granted`
- **Flow token:** `Session.grant { userId, sessionId, outcome }`

### `lookup(sessionId: SessionId) -> FOUND | error:unknown`

- **Inputs:** `sessionId: SessionId`
- **Outcomes (enum):** `FOUND`, `error:unknown`
- **Flow token:** `Session.lookup { sessionId, outcome }`
