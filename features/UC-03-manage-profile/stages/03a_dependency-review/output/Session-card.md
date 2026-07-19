# Dependency review — `Session`

## Section 1 — Invocations received

| Action | Flow (sync) | Data received | Pattern | Source |
|---|---|---|---|---|
| `lookup` | `WhenWebHandleRoutedThenSessionLookupForManageProfile` | token | A | trigger token |
| `grant` | `WhenUserGetProfileFoundThenSessionGrantForManageProfile` | userId | A | trigger token |

## Section 2 — Named-region reads by others (inbound Pattern D)

None.

## Route-filter analysis

Session.lookup is shared across multiple flows (login, profile). The sync `WhenWebHandleRoutedThenSessionLookupForManageProfile` uses route filter `auth`.
