# Responsibility map — Sign In

## Derivation rubric

| Use-case responsibility / branch | Candidate concept | Why this capability is separate | Why not the bootstrap concept? | Why not another listed concept? |
|---|---|---|---|---|
| Receive POST /api/users/login | `Web` | Bootstrap — owns transport boundary | — | — |
| Validate fields are non-empty | `Web` | Request format check, no business state | Already bootstrap | — |
| Look up Member by email | `User` | Owns user identity (username, email) | Needs persistence | Email is part of User's state |
| Verify password | `PasswordAuth` | Owns password hash and verification | Distinct lifecycle (failed attempts, lockout) | User owns identity, not credentials |
| Mint JWT session token | `Session` | Owns session/token lifecycle | Needs persistence | Separate lifecycle from user/credentials |
| Return user profile + token | `Web` (respond) | Transport output | Already bootstrap | — |
| Detect blank field → 422 | `Web` | Transport-level validation | Already bootstrap | — |
| Detect unknown email → 401 | `User` (failure) + `Web` (respond) | User detects not found, Web translates to HTTP | — | — |
| Detect wrong password → 401 | `PasswordAuth` (failure) + `Web` (respond) | PasswordAuth detects mismatch, Web responds | — | — |
| Detect locked account → 401 | `PasswordAuth` (failure) + `Web` (respond) | PasswordAuth detects lock, Web responds | — | — |

## Concepts

| Concept | Owned state (one line) | Owned actions | Notes |
|---|---|---|---|
| `Web` | route table | `handle`, `respond` | Bootstrap concept — see `methodology/architecture/WEB_CONCEPT.md` |
| `User` | username, email, passwordHash, bio, image | `lookupByEmail`, `register`, `lookupByUsername` | `lookupByEmail` is new for UC-02; others already exist |
| `PasswordAuth` | passwordHash, failedAttempts, lockedUntil | `setCredential`, `check` | Already implemented in UC-00 |
| `Session` | token → userId mapping | `grant`, `lookup` | Already implemented in UC-00 |

## Coverage check

| Scenario | Concepts touched |
|---|---|
| `sign-in` | `Web`, `User`, `PasswordAuth`, `Session` |
| `sign-in ext 2a — blank field` | `Web` |
| `sign-in ext 3a — unknown email` | `Web`, `User` |
| `sign-in ext 4a — wrong password` | `Web`, `User`, `PasswordAuth` |
| `sign-in ext 4b — account locked` | `Web`, `User`, `PasswordAuth` |

## Out of scope

- `Profile` — the profile response is assembled from `User` state (username, email, bio, image) plus `Session` token. No separate Profile concept needed.
