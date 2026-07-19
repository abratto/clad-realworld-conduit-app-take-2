# Sync test derivation — UC-02-sign-in

## Derivation map

| Sync spec | When trigger | Then target | Test class | Source |
|---|---|---|---|---|
| `WhenWebHandleRoutedThenUserLookupByEmailForSignIn` | `Web/handle[Routed]` | `User/lookupByEmail` | `WhenWebHandleRoutedThenUserLookupByEmailForSignInTest` | Chain row 3 |
| `WhenUserLookupByEmailFoundThenPasswordAuthCheckForSignIn` | `User/lookupByEmail[FOUND]` | `PasswordAuth/check` | `WhenUserLookupByEmailFoundThenPasswordAuthCheckForSignInTest` | Chain row 4 |
| `WhenUserLookupByEmailRefusedThenWebRespondForSignIn` | `User/lookupByEmail[refused]` | `Web/respond[401]` | `WhenUserLookupByEmailRefusedThenWebRespondForSignInTest` | Chain row 5 |
