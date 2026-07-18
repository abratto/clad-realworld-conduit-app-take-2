# Session — SPEC

## Actions

### `grant(userId: UserId) -> Granted`

- **Inputs:** `userId: UserId`
- **Outcomes (enum):** `Granted`
- **Flow token:** `Session.grant { userId, sessionId, outcome }`
