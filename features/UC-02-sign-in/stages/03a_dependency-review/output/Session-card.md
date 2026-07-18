# Dependency review — `Session`

## Section 1 — Invocations received

| Action | Flow (sync) | Data received | Pattern | Source |
|---|---|---|---|---|
| `grant` | `WhenPasswordAuthCheckOkThenSessionGrantForLogin` (sign-in) | userId | A | `PasswordAuth.check` outcome (`OK` token) |

## Section 2 — Named-region reads by others (inbound Pattern D)

None — no other concept's sync reads `Session` named region.

## Route-filter analysis

`Session.grant` is invoked by both UC-01 (register) and UC-02 (sign-in). The sync `WhenPasswordAuthCheckOkThenSessionGrantForLogin` triggers on `PasswordAuth.check[OK]`, which is unique to UC-02. No route filter needed for the trigger — the trigger action itself is scoped.

However, `Session.grant[Granted]` is produced by both UCs, so `WhenSessionGrantGrantedThenWebRespondForLogin` must carry a route filter.

## Inconsistencies and risks

- None.
