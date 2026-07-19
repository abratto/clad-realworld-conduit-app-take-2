# Concept test derivation — UC-02-sign-in

## Derivation map

### `User.lookupByEmail` → test class: `UserLookupByEmailTest`

| # | Test method | Outcome | Source | Preconditions | Arrange |
|---|---|---|---|---|---|
| 1 | shouldReturnUserIdWhenEmailFound | FOUND | 04c sign-in happy path | existing user with email | seed user + email, write invocation |
| 2 | shouldRefuseWhenEmailUnknown | refused | 04c sign-in unknown email | no user with email | write invocation with unknown email |
