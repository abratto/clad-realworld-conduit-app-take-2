# User — SPEC

## Actions

### `lookupByUsername(username: String) -> FOUND | refused`
- **Inputs:** `username: String`
- **Outcomes (enum):** `FOUND`, `refused`
- **Flow token:** `User.lookupByUsername { username, userId, username, bio, image, outcome }`

## Response shapes

### `GET /api/profiles/:username`

- **Success wrapper:** `{"profile": {...}}`
- **Required fields (200):**
  - `$.profile.username` — `String`
  - `$.profile.bio` — `String | null`
  - `$.profile.image` — `String | null`
  - `$.profile.following` — `Boolean`
- **Not found (404):**
  - `$.errors.profile[0]` — `"not found"`
