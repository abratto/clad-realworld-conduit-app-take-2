# Responsibility map — Register Account

## Derivation rubric

| Use-case responsibility / branch | Candidate concept | Why this capability is separate | Why not the bootstrap concept? | Why not another listed concept? |
|---|---|---|---|---|
| Receive POST /api/users | `Web` | Bootstrap — owns transport boundary | — | — |
| Validate required fields are non-empty | `Web` | Request format check, no business state | Already bootstrap | — |
| Check username uniqueness | `User` | Business invariant on user identity | Needs to read/write user state | — |
| Check email uniqueness | `User` | Business invariant on user identity | Needs to read/write user state | Already in User (same invariant check) |
| Create user record (username, email, password hash, bio, image) | `User` | Owns user identity lifecycle | Needs persistence | — |
| Mint JWT session token | `Session` | Owns session/token lifecycle | Needs persistence | Session has different lifecycle (token expiry, refresh) |
| Detect duplicate and return 409 | `Web` (respond) + `User` (detect failure) | User detects failure, Web translates to HTTP | — | — |
| Detect blank field and return 422 | `Web` | Transport-level validation | Already bootstrap | — |

## Concepts

| Concept | Owned state (one line) | Owned actions | Notes |
|---|---|---|---|
| `Web` | route table | `handle`, `respond` | Bootstrap concept — see `methodology/architecture/WEB_CONCEPT.md` |
| `User` | username, email, passwordHash, bio, image | `register` | Password hashing occurs inside register |
| `Session` | token → userId mapping | `grant` | Grants a JWT session token for an authenticated user |

## Coverage check

| Scenario | Concepts touched |
|---|---|
| `register-account` | `Web`, `User`, `Session` |
| `register-account ext 2a — blank field` | `Web` |
| `register-account ext 3a — duplicate username` | `Web`, `User` |
| `register-account ext 4a — duplicate email` | `Web`, `User` |

## Out of scope

- `PasswordAuth` — password hashing is an internal detail of the User/register action, not a separate concept for registration. The existing `PasswordAuth` concept from UC-02-sign-in will own password verification on login.
