# Dependency review — `Web`

## Section 1 — Invocations received

| Action | Flow (sync) | Data received | Pattern | Source |
|---|---|---|---|---|
| `respond` | `WhenWebHandleRefusedThenWebRespondForLogin` (sign-in) | status: 422, body: error envelope | C | sync constants |
| `respond` | `WhenUserLookupByEmailRefusedThenWebRespondForLogin` (sign-in) | status: 401, body: credentials error | C | sync constants |
| `respond` | `WhenPasswordAuthCheckBadPasswordThenWebRespondForLogin` (sign-in) | status: 401, body: credentials error | C | sync constants |
| `respond` | `WhenPasswordAuthCheckLockedThenWebRespondForLogin` (sign-in) | status: 401, body: credentials error | C | sync constants |
| `respond` | `WhenSessionGrantGrantedThenWebRespondForLogin` (sign-in) | status: 200, body: user object | A + C + D | trigger token + sync constants + User state |

## Section 2 — Named-region reads by others (inbound Pattern D)

None — Web has no named state region.

## Route-filter analysis

| Sync | Trigger | Produced by multiple routes? | Route filter |
|---|---|---|---|
| `WhenWebHandleRefusedThenWebRespondForLogin` | `Web.handle[Refused:blankFields]` | Yes | Required — filter on route `POST /api/users/login` |
| `WhenUserLookupByEmailRefusedThenWebRespondForLogin` | `User.lookupByEmail[refused]` | No — unique to login | Not needed |
| `WhenPasswordAuthCheckBadPasswordThenWebRespondForLogin` | `PasswordAuth.check[error:badPassword]` | No — unique to login | Not needed |
| `WhenPasswordAuthCheckLockedThenWebRespondForLogin` | `PasswordAuth.check[error:locked]` | No — unique to login | Not needed |
| `WhenSessionGrantGrantedThenWebRespondForLogin` | `Session.grant[Granted]` | Yes — also UC-01 register | Required — filter on route `POST /api/users/login` |

## Inconsistencies and risks

- `Web.respond` is called with different pattern mixes across flows (C-only, A+C+D). Expected — `respond` is generic.
- `Session.grant[Granted]` will be produced by both UC-01 and UC-02. The sync `WhenSessionGrantGrantedThenWebRespondForLogin` must carry a route filter.

## Cross-checks

- Every `Sync` named here exists in `../../03_syncs/output/`.
- No Pattern D rows — Web has no named state region.
