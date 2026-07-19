# Dependency review — `Follow`
## Section 1 — Invocations received
| Action | Flow (sync) | Data received | Pattern | Source |
|---|---|---|---|---|
| `isFollowing` | `WhenSessionLookupFoundThenFollowIsFollowingForViewProfile` | followerId, profileId | A + B | trigger token + sibling output |
## Section 2 — Inbound Pattern D: None
