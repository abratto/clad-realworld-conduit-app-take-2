# Sync test derivation — UC-03-manage-profile

## Derivation map

| Sync spec | When trigger | Then target | Test class | Source |
|---|---|---|---|---|
| `WhenSessionLookupFoundThenUserGetProfileForManageProfile` | `Session/lookup[FOUND]` | `User/getProfile` | `WhenSessionLookupFoundThenUserGetProfileForManageProfileTest` | Chain row 5 |
| `WhenUserGetProfileFoundThenSessionGrantForManageProfile` | `User/getProfile[FOUND]` | `Session/grant` | `WhenUserGetProfileFoundThenSessionGrantForManageProfileTest` | Chain row 6 |
