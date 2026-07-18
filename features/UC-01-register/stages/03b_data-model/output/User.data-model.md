# User — conceptual data model

## Step 1 — Familiar examples and elementary facts

### Familiar examples

- User ada has username "ada@example.com", bio "Ada Lovelace", image null.
- User bob has username "bob@example.com", bio null, image "https://example.com/bob.jpg".
- No two users share the same username or email.

### Elementary facts

- User has username.
- User has email.
- User has passwordHash.
- User has bio (optional).
- User has image (optional).

### Step 1 quality checks

- Object identification: `User` with internal `UserId` identifier.
- Split/recombine: each fact type above decomposes the `User` concept's state section; all fields are in 1:1 correspondence with a User.

## Step 2 — Draft fact model and population check

### Object types

- Entity: `User (UserId)`
- Value: `String` (for username, email, passwordHash, bio, image)

### Fact types

- `User(UserId) has username String` — 1:1
- `User(UserId) has email String` — 1:1
- `User(UserId) has passwordHash String` — 1:1
- `User(UserId) has bio String` — 1:1, optional (null allowed)
- `User(UserId) has image String` — 1:1, optional (null allowed)

### Population check

- `User(u1) has username "ada"`, `User(u1) has email "ada@test.com"`, `User(u1) has passwordHash "..."`, `User(u1) has bio null`, `User(u1) has image null`.

## Step 3 — Combination and arithmetic derivation checks

### Entity-type combination check

- None. Each fact type involves exactly one entity type (`User`).

### Arithmetic derivations

- None. All fields are stored directly.

## Step 4 — Uniqueness constraints and arity checks

### Uniqueness constraints

- `User(UserId) has username String` — uniqueness constraint on username (no two Users share a username).
- `User(UserId) has email String` — uniqueness constraint on email (no two Users share an email).

### Arity checks

- All fact types are unary (1:1 between UserId and the value).

## Step 5 — Mandatory roles and logical derivations

### Mandatory role constraints

- Every `User` must have a username (mandatory).
- Every `User` must have an email (mandatory).
- Every `User` must have a passwordHash (mandatory).
- `bio` and `image` are optional — a User may have null for either.

### Logical derivations

- None. All fields are stored directly.

## Step 6 — Value, set comparison, and subtype constraints

### Value constraints

- None. Username, email, bio, image are free-form strings; passwordHash is a hash output string.

### Set comparison constraints

- The ranges of username and email are disjoint in practice but not enforced schematically — they are stored in separate fields.

### Subtype constraints

- None.

## Step 7 — Other constraints and final checks

### Other constraints

- None.

### Final checks

- All five fact types trace to the `User` concept's `state` section in `User.concept.md`.
- Every Pattern D field from `pattern-d-summary.md` (`username`, `email`) is present in this model.

## Modeling Notes

- `bio` and `image` are nullable strings that default to `null` on registration. The API spec treats them as nullable (`type: [string, null]`), so the conceptual model accurately represents them as optional 1:1 facts.
- `passwordHash` is opaque to all other concepts — it is write-only from the `register` action and never read by any sync.
