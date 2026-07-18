# Dependency review — `User`

## Section 1 — Invocations received

| Action | Flow (sync) | Data received | Pattern | Source |
|---|---|---|---|---|
| `getProfile` | `WhenSessionLookupFoundThenUserGetProfileForProfile` | userId | A | trigger token |

## Section 2 — Named-region reads by others (inbound Pattern D)

| Field | Read by (sync) | In flow | Pattern | Key |
|---|---|---|---|---|
| `username` | `WhenSessionGrantGrantedThenWebRespondForProfile` | profile | D | `?userId` |
| `email` | `WhenSessionGrantGrantedThenWebRespondForProfile` | profile | D | `?userId` |
| `bio` | `WhenSessionGrantGrantedThenWebRespondForProfile` | profile | D | `?userId` |
| `image` | `WhenSessionGrantGrantedThenWebRespondForProfile` | profile | D | `?userId` |
