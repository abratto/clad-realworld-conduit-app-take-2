# Session — conceptual data model

## Step 1 — Familiar examples and elementary facts

### Familiar examples

- Session s1 is for user ada, opened at 10:00.

### Elementary facts

- Session is for User (userId).
- Session was opened at time (openedAt).

### Step 1 quality checks

- Object identification: `Session` with internal `SessionId` identifier.
- Split/recombine: each fact type corresponds to one concept state field.

## Step 2 — Draft fact model and population check

### Object types

- Entity: `Session (SessionId)`
- Value: `UserId` (opaque reference), `Timestamp`

### Fact types

- `Session(SessionId) is for User(UserId)` — 1:1
- `Session(SessionId) openedAt Timestamp` — 1:1

### Population check

- `Session(s1) is for User(u1)`, `Session(s1) openedAt 10:00`.

## Step 3 — Combination and arithmetic derivation checks

### Entity-type combination check

- None.

### Arithmetic derivations

- None.

## Step 4 — Uniqueness constraints and arity checks

### Uniqueness constraints

- None beyond SessionId identity.

### Arity checks

- All fact types are unary (1:1).

## Step 5 — Mandatory roles and logical derivations

### Mandatory role constraints

- userId, openedAt: mandatory.

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

- No Pattern D reads target Session state.

## Modeling Notes

- `UserId` is an opaque entity reference, not a foreign key.
