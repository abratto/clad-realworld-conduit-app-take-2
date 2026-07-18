# Dependency review — `Session`

## Section 1 — Invocations received

| Action | Flow (sync) | Data received | Pattern | Source |
|---|---|---|---|---|
| `lookup` | `WhenWebHandleRoutedThenSessionLookupForProfile` | token | A | trigger token |
| `grant` | `WhenUserGetProfileFoundThenSessionGrantForProfile` | userId | A | trigger token |

## Section 2 — Named-region reads by others (inbound Pattern D)

None.

## Route-filter analysis

Session.lookup is shared across multiple flows (login, profile). The sync `WhenWebHandleRoutedThenSessionLookupForProfile` uses route filter `auth`.
