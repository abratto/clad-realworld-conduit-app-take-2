# Concept test derivation — UC-03-manage-profile

## Derivation map

### `User.getProfile` → test class: `UserGetProfileTest`

| # | Test method | Outcome | Source | Preconditions | Arrange |
|---|---|---|---|---|---|
| 1 | shouldReturnProfileWhenUserExists | FOUND | 04c view-profile | existing user | seed user, write invocation with userId |
