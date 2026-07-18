# Dependency review — `Session`

## Section 1 — Invocations received

| Action | Flow (sync) | Data received | Pattern | Source |
|---|---|---|---|---|
| `grant` | `WhenUserRegisterRegisteredThenSessionGrantForRegister` (`register-account`) | userId | A | `User.register` outcome (`Registered` token carries `?userId`) |

## Section 2 — Named-region reads by others (inbound Pattern D)

None — no other concept's sync reads the `Session` named region.

## Route-filter analysis

`Session.grant` will be invoked by both UC-01-register and UC-02-sign-in. The sync `WhenUserRegisterRegisteredThenSessionGrantForRegister` triggers on `User.register[Registered]`, which is unique to UC-01. So the route filter is not needed for this sync's trigger — the trigger action itself is scoped.

However, `Session.grant[Granted]` will be produced by both UC-01 and UC-02, so syncs firing on that outcome (e.g., `WhenSessionGrantGrantedThenWebRespondForRegister`) must carry a route filter.

## Inconsistencies and risks

- None. `Session.grant` has a single consistent caller in this feature.

## Cross-checks

- Every `Action` row exists in `Session.concept.md`.
- Every `Sync` named here exists in `../../03_syncs/output/`.
- No Pattern D rows — Session state is not read by any other concept's sync.
- Every copied token matches the approved sync text exactly.
