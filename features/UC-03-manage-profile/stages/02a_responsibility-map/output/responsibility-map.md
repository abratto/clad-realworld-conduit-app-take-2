# Responsibility map — Manage Profile

## Derivation rubric

| Use-case responsibility / branch | Candidate concept | Why this capability is separate | Why not the bootstrap concept? | Why not another listed concept? |
|---|---|---|---|---|
| Receive GET/PUT /api/user | `Web` | Bootstrap — transport boundary | — | — |
| Validate JWT session token | `Session` | Owns session lifecycle | Needs persistence | — |
| Look up user by session | `Session` + `User` | Session maps token→userId, User has profile data | — | — |
| Update profile fields | `User` | Owns user identity (username, email, bio, image) | Needs persistence | — |
| Update password | `PasswordAuth` | Owns credential management | Needs persistence | User owns identity, not credentials |
| Mint new JWT on update | `Session` | Token rotation on profile change | — | — |

## Concepts

| Concept | Owned state (one line) | Owned actions | Notes |
|---|---|---|---|
| `Web` | route table | `handle`, `respond` | Bootstrap concept |
| `Session` | token → userId mapping | `lookup`, `grant` | Validate token, mint new one on update |
| `User` | username, email, passwordHash, bio, image | `register`, `lookupByEmail`, `lookupByUsername`, `getProfile`, `updateProfile` | New actions: `getProfile`, `updateProfile` |
| `PasswordAuth` | passwordHash, failedAttempts, lockedUntil | `setCredential`, `check` | Only needed when password is updated |

## Coverage check

| Scenario | Concepts touched |
|---|---|
| `view-profile` | `Web`, `Session`, `User` |
| `update-profile ext 3a — blank field` | `Web`, `Session`, `User` |
| `update-profile ext 3b — duplicate email/username` | `Web`, `Session`, `User` |
| `update-profile ext 4a — short password` | `Web`, `Session`, `PasswordAuth` |
| `update-profile ext 4b — empty password` | `Web`, `Session`, `PasswordAuth` |

## Out of scope

- `Profile` — profile data is assembled from `User` state. No separate concept needed.
