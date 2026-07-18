# PasswordAuth — SPEC

## Actions

### `check(userId: UserId, password: String) -> OK | error:badPassword | error:locked`

- **Inputs:** `userId: UserId`, `password: String`
- **Outcomes (enum):** `OK`, `error:badPassword`, `error:locked`
- **Flow token:** `PasswordAuth.check { userId, outcome }`

### `setCredential(userId: UserId, password: String) -> ok`

- **Inputs:** `userId: UserId`, `password: String`
- **Outcomes (enum):** `ok`
- **Flow token:** `PasswordAuth.setCredential { userId, outcome }`
