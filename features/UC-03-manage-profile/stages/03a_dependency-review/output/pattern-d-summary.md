# Pattern D summary — UC-03

## Pattern D reads

| Consumer (sync) | Field read | Owner concept | Key | In flow |
|---|---|---|---|---|
| `WhenSessionGrantGrantedThenWebRespondForManageProfile` | `username` | `User` | `?userId` | profile |
| `WhenSessionGrantGrantedThenWebRespondForManageProfile` | `email` | `User` | `?userId` | profile |
| `WhenSessionGrantGrantedThenWebRespondForManageProfile` | `bio` | `User` | `?userId` | profile |
| `WhenSessionGrantGrantedThenWebRespondForManageProfile` | `image` | `User` | `?userId` | profile |
