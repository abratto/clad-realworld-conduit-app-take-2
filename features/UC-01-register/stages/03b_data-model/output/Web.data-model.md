# Web — conceptual data model

## Step 1 — Familiar examples and elementary facts

### Familiar examples

- Web has no stored state — it is a stateless bootstrap transport concept.

### Elementary facts

- None. `Web` owns no data.

### Step 1 quality checks

- Object identification: N/A — Web has no state.
- Split/recombine: N/A.

## Step 2 — Draft fact model and population check

### Object types

- None.

### Fact types

- None.

### Population check

- N/A (Web is stateless).

## Step 3 — Combination and arithmetic derivation checks

### Entity-type combination check

- N/A — Web has no state.

### Arithmetic derivations

- N/A.

## Step 4 — Uniqueness constraints and arity checks

### Uniqueness constraints

- N/A.

### Arity checks

- N/A.

## Step 5 — Mandatory roles and logical derivations

### Mandatory role constraints

- N/A.

### Logical derivations

- N/A.

## Step 6 — Value, set comparison, and subtype constraints

### Value constraints

- N/A.

### Set comparison constraints

- N/A.

### Subtype constraints

- N/A.

## Step 7 — Other constraints and final checks

### Other constraints

- N/A.

### Final checks

- `Web` is the bootstrap transport concept with no persistent state. Its route table is ephemeral (wired at compile time) and not part of the conceptual data model.

## Modeling Notes

- Bootstrap concepts (Web, Grpc, Cli, Stream) own no persistent data by definition. Their route tables are configuration, not state.
