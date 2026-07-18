# User — SPEC

## Actions

### `getProfile(userId: UserId) -> FOUND | refused`
- **Inputs:** `userId: UserId`
- **Outcomes (enum):** `FOUND`, `refused`
- **Flow token:** `User.getProfile { userId, username, email, bio, image, outcome }`

### `updateProfile(userId: UserId, username?: String, email?: String, bio?: String, image?: String) -> Updated | refused`
- **Inputs:** `userId: UserId`, `username: String?`, `email: String?`, `bio: String?`, `image: String?`
- **Outcomes (enum):** `Updated`, `refused`
- **Flow token:** `User.updateProfile { userId, outcome }`

### `register(username: String, email: String, password: String) -> Registered | refused`
- **Outcomes (enum):** `Registered`, `refused`

### `lookupByEmail(email: String) -> FOUND | refused`
- **Outcomes (enum):** `FOUND`, `refused`

### `lookupByUsername(username: String) -> FOUND | refused`
- **Outcomes (enum):** `FOUND`, `refused`

## Response shapes

### `GET /api/user`
- **Success wrapper:** `{"user": {...}}`
- **Required fields (200):**
  - `$.user.email` — `String`
  - `$.user.token` — `String` (JWT)
  - `$.user.username` — `String`
  - `$.user.bio` — `String | null`
  - `$.user.image` — `String | null`
- **Unauthorized (401):**
  - `$.errors.token[0]` — `"is missing"` or `"is invalid"`

### `PUT /api/user`
- Same response shape as GET /api/user
- Validation error (422): `{"errors": {"<field>": ["<message>"]}}`
