# Sync test derivation — UC-02-sign-in

## Derivation map

| Sync spec | When trigger | Then target | Test class | Source |
|---|---|---|---|---|
| `WhenWebHandleRoutedThenUserLookupByEmailForLogin` | `Web/handle[Routed]` | `User/lookupByEmail` | `WhenWebHandleRoutedThenUserLookupByEmailForLoginTest` | Chain row 3 |
| `WhenUserLookupByEmailFoundThenPasswordAuthCheckForLogin` | `User/lookupByEmail[FOUND]` | `PasswordAuth/check` | `WhenUserLookupByEmailFoundThenPasswordAuthCheckForLoginTest` | Chain row 4 |
| `WhenUserLookupByEmailRefusedThenWebRespondForLogin` | `User/lookupByEmail[refused]` | `Web/respond[401]` | `WhenUserLookupByEmailRefusedThenWebRespondForLoginTest` | Chain row 5 |
