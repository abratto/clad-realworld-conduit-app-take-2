# Dependency review — `Web`

## Section 1 — Invocations received

| Action | Flow (sync) | Data received | Pattern | Source |
|---|---|---|---|---|
| `respond` | `WhenWebHandleNoTokenThenWebRespondForManageProfile` | status: 401, body: error envelope | C | sync constants |
| `respond` | `WhenSessionGrantGrantedThenWebRespondForManageProfile` | status: 200, body: user object | A + C + D | trigger token + constants + User state |

## Section 2 — Named-region reads by others (inbound Pattern D)

None.

## Route-filter analysis

No shared triggers across flows — `auth` route is unique to profile management.
