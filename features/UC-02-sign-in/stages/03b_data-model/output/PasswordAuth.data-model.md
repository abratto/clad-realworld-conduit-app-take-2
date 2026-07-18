# PasswordAuth — conceptual data model

## Step 1 — Familiar examples and elementary facts

### Familiar examples

- PasswordAuth for ada has hash "...", 0 failed attempts, no lockout.
- PasswordAuth for bob has hash "...", 3 failed attempts, locked until 12:15.

### Elementary facts

- PasswordAuth has passwordHash.
- PasswordAuth has failedAttempts.
- PasswordAuth has lockedUntil (optional).

### Step 1 quality checks

- Object identification: `PasswordAuth` with `UserId` as entity identifier.
- Split/recombine: each fact type corresponds to one concept state field.

## Step 2 — Draft fact model and population check

### Object types

- Entity: `PasswordAuth (UserId)`
- Value: `PasswordHash`, `Int`, `Timestamp`

### Fact types

- `PasswordAuth(UserId) has passwordHash PasswordHash` — 1:1
- `PasswordAuth(UserId) has failedAttempts Int` — 1:1
- `PasswordAuth(UserId) has lockedUntil Timestamp` — 1:1, optional

### Population check

- `PasswordAuth(u1) has passwordHash "..."`, `PasswordAuth(u1) has failedAttempts 0`.

## Step 3 — Combination and arithmetic derivation checks

### Entity-type combination check

- None.

### Arithmetic derivations

- None.

## Step 4 — Uniqueness constraints and arity checks

### Uniqueness constraints

- None beyond UserId identity.

### Arity checks

- All fact types are unary (1:1).

## Step 5 — Mandatory roles and logical derivations

### Mandatory role constraints

- passwordHash, failedAttempts: mandatory.
- lockedUntil: optional.

### Logical derivations

- None.

## Step 6 — Value, set comparison, and subtype constraints

### Value constraints

- failedAttempts is a non-negative integer.

### Set comparison constraints

- None.

### Subtype constraints

- None.

## Step 7 — Other constraints and final checks

### Other constraints

- None.

### Final checks

- No Pattern D reads target PasswordAuth state.

## Modeling Notes

- `failedAttempts` defaults to 0. `lockedUntil` is null unless account is locked.
