# Dependency review — `User`

## Section 1 — Invocations received

| Action | Flow (sync) | Data received | Pattern | Source |
|---|---|---|---|---|
| `getProfile` | `WhenSessionLookupFoundThenUserGetProfileForManageProfile` | userId | A | trigger token |

## Section 2 — Named-region reads by others (inbound Pattern D)

| Field | Read by (sync) | In flow | Pattern | Key |
|---|---|---|---|---|
| `username` | `WhenSessionGrantGrantedThenWebRespondForManageProfile` | profile | D | `?userId` |
| `email` | `WhenSessionGrantGrantedThenWebRespondForManageProfile` | profile | D | `?userId` |
| `bio` | `WhenSessionGrantGrantedThenWebRespondForManageProfile` | profile | D | `?userId` |
| `image` | `WhenSessionGrantGrantedThenWebRespondForManageProfile` | profile | D | `?userId` |
