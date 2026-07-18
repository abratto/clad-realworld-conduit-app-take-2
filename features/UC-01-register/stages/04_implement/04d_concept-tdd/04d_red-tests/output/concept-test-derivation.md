# Concept test derivation — UC-01-register

## Derivation map

### `User.register` → test class: `UserRegisterTest`

| # | Test method | Outcome | Source | Preconditions | Arrange |
|---|---|---|---|---|---|
| 1 | shouldRegisterUserWithUniqueUsernameAndEmail | Registered | 04c register-account happy path | no existing user with username or email | write pending invocation with unique credentials |
| 2 | shouldRefuseWhenUsernameAlreadyExists | refused | 04c register-account duplicate username | existing user with same username | seed user with target username |
| 3 | shouldRefuseWhenEmailAlreadyExists | refused | 04c register-account duplicate email | existing user with same email | seed user + seed email with target address |

### `Session.grant` → test class: `SessionGrantTest`

| # | Test method | Outcome | Source | Preconditions | Arrange |
|---|---|---|---|---|---|
| 1 | shouldMintSessionTokenAndReturnUserId | Granted | 04c register-account happy path (step 6) | user already exists | write pending invocation with userId |

## Handoff bundle

| Item | Value |
|---|---|
| **Approved test files** | `UserRegisterTest.java`, `SessionGrantTest.java` |
| **Red evidence command** | `mvn test -Dtest="UserRegisterTest,SessionGrantTest"` |
| **Expected red outcome** | 3 behavioral failures: (1) User.register outcome `REGISTERED` vs `Registered`, (2) email uniqueness not checked, (3) Session.grant outcome `GRANTED` vs `Granted` |
| **Next implementation target** | `UserConcept.java`: change `"REGISTERED"` to `"Registered"`, add email uniqueness + storage. `SessionConcept.java`: change `"GRANTED"` to `"Granted"`. |
