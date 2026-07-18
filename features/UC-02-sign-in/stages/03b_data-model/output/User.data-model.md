# User — conceptual data model

## Step 1 — Familiar examples and elementary facts

### Familiar examples

- User ada has username "ada", email "ada@test.com", passwordHash "...", bio "Ada Lovelace", image null.

### Elementary facts

- User has username.
- User has email.
- User has passwordHash.
- User has bio (optional).
- User has image (optional).

### Step 1 quality checks

- Object identification: `User` with internal `UserId` identifier.
- Split/recombine: each fact type decomposes the `User` concept's state section.

## Step 2 — Draft fact model and population check

### Object types

- Entity: `User (UserId)`
- Value: `String`

### Fact types

- `User(UserId) has username String` — 1:1, unique
- `User(UserId) has email String` — 1:1, unique
- `User(UserId) has passwordHash String` — 1:1
- `User(UserId) has bio String` — 1:1, optional
- `User(UserId) has image String` — 1:1, optional

### Population check

- `User(u1) has username "ada"`, `User(u1) has email "ada@test.com"`.

## Step 3 — Combination and arithmetic derivation checks

### Entity-type combination check

- None.

### Arithmetic derivations

- None.

## Step 4 — Uniqueness constraints and arity checks

### Uniqueness constraints

- username unique across all users.
- email unique across all users.

### Arity checks

- All fact types are unary (1:1).

## Step 5 — Mandatory roles and logical derivations

### Mandatory role constraints

- username, email, passwordHash: mandatory.
- bio, image: optional.

### Logical derivations

- None.

## Step 6 — Value, set comparison, and subtype constraints

### Value constraints

- None.

### Set comparison constraints

- None.

### Subtype constraints

- None.

## Step 7 — Other constraints and final checks

### Other constraints

- None.

### Final checks

- All Pattern D fields from 03a (username, email, bio, image) are present.

## Modeling Notes

- PasswordHash is opaque to all other concepts.
