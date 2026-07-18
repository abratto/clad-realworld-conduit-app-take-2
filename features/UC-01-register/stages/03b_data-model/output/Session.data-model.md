# Session — conceptual data model

## Step 1 — Familiar examples and elementary facts

### Familiar examples

- Session for ada was opened at 2026-07-18T10:00:00Z, with a JWT token.
- Session for bob was opened at 2026-07-18T10:05:00Z, with a JWT token.

### Elementary facts

- Session is for User (userId).
- Session was opened at time (openedAt).

### Step 1 quality checks

- Object identification: `Session` with internal `SessionId` identifier.
- Split/recombine: each fact type corresponds to one field in the `Session` concept's state section.

## Step 2 — Draft fact model and population check

### Object types

- Entity: `Session (SessionId)`
- Value: `UserId` (opaque reference to User entity), `Timestamp` (openedAt)

### Fact types

- `Session(SessionId) is for User(UserId)` — 1:1
- `Session(SessionId) openedAt Timestamp` — 1:1

### Population check

- `Session(s1) is for User(u1)`, `Session(s1) openedAt 2026-07-18T10:00:00Z`.

## Step 3 — Combination and arithmetic derivation checks

### Entity-type combination check

- None. Each fact type involves exactly one entity type (`Session`).

### Arithmetic derivations

- None. All fields are stored directly.

## Step 4 — Uniqueness constraints and arity checks

### Uniqueness constraints

- None beyond the internal `SessionId` identity.

### Arity checks

- All fact types are unary (1:1 between SessionId and the value).

## Step 5 — Mandatory roles and logical derivations

### Mandatory role constraints

- Every `Session` must have a `userId` (mandatory).
- Every `Session` must have an `openedAt` (mandatory).

### Logical derivations

- None.

## Step 6 — Value, set comparison, and subtype constraints

### Value constraints

- `openedAt` must be a valid timestamp (system-generated, not user-supplied).

### Set comparison constraints

- None.

### Subtype constraints

- None.

## Step 7 — Other constraints and final checks

### Other constraints

- `SessionId` must be unguessable (e.g., 128-bit random), but this is an implementation property, not a conceptual constraint.

### Final checks

- Both fact types trace to the `Session` concept's `state` section in `Session.concept.md`.
- No Pattern D reads target `Session` state — no additional fields needed.

## Modeling Notes

- `Session` references `User` by opaque `UserId`. This is a conceptual entity reference, not a foreign key — the `Session` concept does not read `User` state; it only holds the identifier.
- The JWT token is not modelled as a separate fact type — it is derived from the `SessionId` at the implementation level. The conceptual model treats the `SessionId` as the bearer token's internal identity.
