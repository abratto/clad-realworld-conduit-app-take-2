# Follow — SPEC

## Actions

### `follow(followerId: UserId, profileId: UserId) -> Followed`
- **Inputs:** `followerId: UserId`, `profileId: UserId`
- **Outcomes (enum):** `Followed`
- **Flow token:** `Follow.follow { followerId, profileId, outcome }`

### `unfollow(followerId: UserId, profileId: UserId) -> Unfollowed`
- **Inputs:** `followerId: UserId`, `profileId: UserId`
- **Outcomes (enum):** `Unfollowed`
- **Flow token:** `Follow.unfollow { followerId, profileId, outcome }`

## Response shapes

### `POST /api/profiles/:username/follow`
- **Success wrapper:** `{"profile": {...}}`
- **Required fields (200):**
  - `$.profile.username` — `String`
  - `$.profile.bio` — `String | null`
  - `$.profile.image` — `String | null`
  - `$.profile.following` — `true`

### `DELETE /api/profiles/:username/follow`
- Same response shape with `following: false`
