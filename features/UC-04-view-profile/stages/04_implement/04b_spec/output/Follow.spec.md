# Follow — SPEC

## Actions

### `isFollowing(followerId: UserId, profileId: UserId) -> true | false`
- **Inputs:** `followerId: UserId`, `profileId: UserId`
- **Outcomes (enum):** `true`, `false`
- **Flow token:** `Follow.isFollowing { followerId, profileId, outcome }`
