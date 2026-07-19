# Pattern D summary — UC-04

## Pattern D reads
| Consumer (sync) | Field read | Owner concept | Key | In flow |
|---|---|---|---|---|
| `WhenUserLookupByUsernameFoundThenSessionLookupForViewProfile` | `token` | `Web` (request input) | `?flow` | view-profile |

No concept-state reads via Pattern D (User state is returned via action outcomes).
