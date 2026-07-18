# 04a — Storage mapping: NOT APPLICABLE

The engine is configured with `engine.dataset.type=tmemory` (in-memory transactional Dataset). No persistent storage mapping is required for this profile. The conceptual data model from Stage 03b is directly realized by the Jena TDB2 named graph schema (`concept:User`, `concept:Session`), which is profile-level configuration, not per-feature design.

See `clad.properties` for the current engine backend.
