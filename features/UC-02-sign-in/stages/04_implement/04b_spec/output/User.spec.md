# User — SPEC

## Actions

### `lookupByEmail(email: String) -> FOUND | refused`

- **Inputs:** `email: String`
- **Outcomes (enum):** `FOUND`, `refused`
- **Flow token:** `User.lookupByEmail { email, userId, username, outcome }`

### `register(username: String, email: String, password: String) -> Registered | refused`

- **Inputs:** `username: String`, `email: String`, `password: String`
- **Outcomes (enum):** `Registered`, `refused`
- **Flow token:** `User.register { username, email, userId, outcome }`

### `lookupByUsername(username: String) -> FOUND | refused`

- **Inputs:** `username: String`
- **Outcomes (enum):** `FOUND`, `refused`
- **Flow token:** `User.lookupByUsername { username, userId, outcome }`

## Response shapes

### `POST /api/users/login`

- **Success wrapper:** `{"user": {...}}`
- **Required fields (200):**
  - `$.user.email` — `String`
  - `$.user.token` — `String` (JWT)
  - `$.user.username` — `String`
  - `$.user.bio` — `String | null`
  - `$.user.image` — `String | null`
- **Authentication error (401):**
  - `$.errors.credentials[0]` — `"invalid"`
- **Validation error (422):**
  - `$.errors.email[0]` — `"can't be blank"`
  - `$.errors.password[0]` — `"can't be blank"`
