# Sync test derivation — UC-01-register

## Derivation map

| Sync spec | When trigger | Then target | Test class | Source |
|---|---|---|---|---|
| `WhenWebHandleBlankFieldsThenWebRespondForRegister` | `Web.handle[refused: blankFields]` | `Web.respond[422]` | `WhenWebHandleBlankFieldsThenWebRespondForRegisterTest` | Chain row 2 |
| `WhenWebHandleRoutedThenUserRegisterForRegister` | `Web.handle[Routed]` | `User.register` | `WhenWebHandleRoutedThenUserRegisterForRegisterTest` | Chain row 3 |
| `WhenUserRegisterRegisteredThenSessionGrantForRegister` | `User.register[Registered]` | `Session.grant` | `WhenUserRegisterRegisteredThenSessionGrantForRegisterTest` | Chain row 4 |
| `WhenUserRegisterDuplicateUsernameThenWebRespondForRegister` | `User.register[refused]` + dup username | `Web.respond[409]` | `WhenUserRegisterDuplicateUsernameThenWebRespondForRegisterTest` | Chain row 5a |
| `WhenUserRegisterDuplicateEmailThenWebRespondForRegister` | `User.register[refused]` + dup email | `Web.respond[409]` | `WhenUserRegisterDuplicateEmailThenWebRespondForRegisterTest` | Chain row 5b |
| `WhenSessionGrantGrantedThenWebRespondForRegister` | `Session.grant[Granted]` | `Web.respond[201]` | `WhenSessionGrantGrantedThenWebRespondForRegisterTest` | Chain row 6 |

## Handoff bundle

| Item | Value |
|---|---|
| **Approved test files** | 6 sync test classes under `app/backend/src/test/java/com/conduit/app/syncs/` |
| **Red evidence command** | `mvn test -Dtest="*Register*"` |
| **Expected red outcome** | Compilation errors (sync implementation classes don't exist yet) |
| **Next implementation target** | 6 SyncAgent classes + 6 sync test classes + Web controller registration endpoint |
