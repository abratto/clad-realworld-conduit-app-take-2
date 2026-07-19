# Dependency review — `User`

## Section 1 — Invocations received

| Action | Flow (sync) | Data received | Pattern | Source |
|---|---|---|---|---|
| `lookupByEmail` | `WhenWebHandleRoutedThenUserLookupByEmailForSignIn` (sign-in) | email | A | `Web.handle` body (`routed` token) |

## Section 2 — Named-region reads by others (inbound Pattern D)

| Field | Read by (sync) | In flow | Pattern | Key |
|---|---|---|---|---|
| `username` | `WhenSessionGrantGrantedThenWebRespondForLogin` | sign-in | D | `?userId` from `Session.grant` outcome |
| `email` | `WhenSessionGrantGrantedThenWebRespondForLogin` | sign-in | D | `?userId` from `Session.grant` outcome |
| `bio` | `WhenSessionGrantGrantedThenWebRespondForLogin` | sign-in | D | `?userId` from `Session.grant` outcome |
| `image` | `WhenSessionGrantGrantedThenWebRespondForLogin` | sign-in | D | `?userId` from `Session.grant` outcome |

## Route-filter analysis

`User.lookupByEmail` is only invoked for UC-02. No route filter needed.

## Inconsistencies and risks

- None. All reads are from the same flow.
