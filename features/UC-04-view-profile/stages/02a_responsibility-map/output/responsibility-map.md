# Responsibility map — View Profile

## Derivation rubric

| Use-case responsibility / branch | Candidate concept | Why this capability is separate | Why not the bootstrap concept? | Why not another listed concept? |
|---|---|---|---|---|
| Receive GET /api/profiles/:username | `Web` | Bootstrap — transport boundary | — | — |
| Look up user by username | `User` | Owns user identity | Needs persistence | — |
| Validate optional JWT token | `Session` | Owns session lifecycle | Bootstrap doesn't manage sessions | — |
| Check if viewer follows profile | `Follow` | Owns follow relationships | Needs persistence | Separate lifecycle from user identity |
| Return profile response | `Web` (respond) | Transport output | Already bootstrap | — |

## Concepts

| Concept | Owned state (one line) | Owned actions | Notes |
|---|---|---|---|
| `Web` | route table | `handle`, `respond` | Bootstrap concept |
| `User` | username, email, passwordHash, bio, image | `lookupByUsername`, `register`, `lookupByEmail`, `getProfile`, `updateProfile` | `lookupByUsername` returns userId for profile resolution |
| `Session` | token → userId mapping | `lookup`, `grant` | Optional — only used when viewer is authenticated |
| `Follow` | follower → followee mapping | `isFollowing` | New action: `isFollowing(followerId, profileId)` returns boolean |

## Coverage check

| Scenario | Concepts touched |
|---|---|
| `view-profile` (authenticated, following) | `Web`, `User`, `Session`, `Follow` |
| `view-profile` (authenticated, not following) | `Web`, `User`, `Session`, `Follow` |
| `view-profile` (unauthenticated) | `Web`, `User` |
| `view-profile` ext 3a — username not found | `Web`, `User` |

## Out of scope

- Follow management (follow/unfollow actions) — deferred to UC-11-follow-user.
- Profile updates — UC-03-manage-profile.
