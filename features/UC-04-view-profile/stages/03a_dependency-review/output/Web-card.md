# Dependency review — `Web`
## Section 1 — Invocations received
| Action | Flow (sync) | Data received | Pattern | Source |
|---|---|---|---|---|
| `respond` | `WhenUserLookupByUsernameRefusedThenWebRespondForViewProfile` | status: 404, body: error | C | constants |
| `respond` | `WhenSessionLookupRefusedThenWebRespondForViewProfile` | status: 200, body: profile + false | C + B | constants + sibling output |
| `respond` | `WhenFollowIsFollowingTrueThenWebRespondForViewProfile` | status: 200, body: profile + true | C | constants |
| `respond` | `WhenFollowIsFollowingFalseThenWebRespondForViewProfile` | status: 200, body: profile + false | C | constants |
## Section 2 — Inbound Pattern D: None
