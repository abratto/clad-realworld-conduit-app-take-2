# Pattern D summary — UC-02-sign-in

## Pattern D reads

| Consumer (sync) | Field read | Owner concept | Key | In flow |
|---|---|---|---|---|
| `WhenSessionGrantGrantedThenWebRespondForLogin` | `username` | `User` | `?userId` (from `Session.grant`) | sign-in |
| `WhenSessionGrantGrantedThenWebRespondForLogin` | `email` | `User` | `?userId` | sign-in |
| `WhenSessionGrantGrantedThenWebRespondForLogin` | `bio` | `User` | `?userId` | sign-in |
| `WhenSessionGrantGrantedThenWebRespondForLogin` | `image` | `User` | `?userId` | sign-in |
| `WhenUserLookupByEmailFoundThenPasswordAuthCheckForLogin` | `password` | `Web` (request input) | `?flow` | sign-in |

## Cross-flow inconsistencies

- No cross-flow inconsistencies — all reads are within the single `sign-in` flow.

## What this feeds

- **Stage 03b (data model).** `User` data model must include `username`, `email`, `bio`, `image` fields.
- **Stage 04a (storage mapping).** `User` storage must make these fields queryable by `userId`.
