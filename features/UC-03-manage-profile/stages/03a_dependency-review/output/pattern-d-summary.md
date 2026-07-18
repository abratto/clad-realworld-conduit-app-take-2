# Pattern D summary — UC-03

## Pattern D reads

| Consumer (sync) | Field read | Owner concept | Key | In flow |
|---|---|---|---|---|
| `WhenSessionGrantGrantedThenWebRespondForProfile` | `username` | `User` | `?userId` | profile |
| `WhenSessionGrantGrantedThenWebRespondForProfile` | `email` | `User` | `?userId` | profile |
| `WhenSessionGrantGrantedThenWebRespondForProfile` | `bio` | `User` | `?userId` | profile |
| `WhenSessionGrantGrantedThenWebRespondForProfile` | `image` | `User` | `?userId` | profile |
