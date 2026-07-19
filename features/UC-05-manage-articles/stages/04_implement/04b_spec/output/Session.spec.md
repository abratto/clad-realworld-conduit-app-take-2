# Session — SPEC

## Actions

### `lookup(token: String) -> FOUND | error:unknown`
- **Inputs:** `token: String`
- **Outcomes (enum):** `FOUND`, `error:unknown`
- **Flow token:** `Session.lookup { token, outcome }`
