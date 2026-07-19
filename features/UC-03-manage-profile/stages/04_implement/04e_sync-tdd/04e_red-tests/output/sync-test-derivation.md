# Sync test derivation — UC-03-manage-profile

## Derivation map

| Sync spec | When trigger | Then target | Test class | Source |
|---|---|---|---|---|
| `WhenSessionLookupFoundThenUserGetProfileForProfile` | `Session/lookup[FOUND]` | `User/getProfile` | `WhenSessionLookupFoundThenUserGetProfileForProfileTest` | Chain row 5 |
| `WhenUserGetProfileFoundThenSessionGrantForProfile` | `User/getProfile[FOUND]` | `Session/grant` | `WhenUserGetProfileFoundThenSessionGrantForProfileTest` | Chain row 6 |
