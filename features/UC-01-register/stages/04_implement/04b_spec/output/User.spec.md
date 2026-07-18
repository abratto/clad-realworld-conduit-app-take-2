# User — SPEC

## Actions

### `register(username: String, email: String, password: String) -> Registered | refused`

- **Inputs:** `username: String`, `email: String`, `password: String`
- **Outcomes (enum):** `Registered`, `refused`
- **Flow token:** `User.register { username, email, userId, outcome }`

## Response shapes

### `POST /api/users`

- **Success wrapper:** `{"user": {...}}`
- **Required fields (201):**
  - `$.user.username` — `String`
  - `$.user.email` — `String`
  - `$.user.bio` — `String | null`
  - `$.user.image` — `String | null`
  - `$.user.token` — `String` (JWT, non-empty)
- **Duplicate username (409):**
  - `$.errors.username[0]` — `"has already been taken"`
- **Duplicate email (409):**
  - `$.errors.email[0]` — `"has already been taken"`
- **Validation error (422):**
  - `$.errors.username[0]` — `"can't be blank"`
  - `$.errors.email[0]` — `"can't be blank"`
  - `$.errors.password[0]` — `"can't be blank"`
