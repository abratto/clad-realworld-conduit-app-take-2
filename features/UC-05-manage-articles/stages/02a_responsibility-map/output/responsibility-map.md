# Responsibility map — Manage Articles

## Derivation rubric

| Responsibility | Candidate concept | Why separate | Not bootstrap? | Not another? |
|---|---|---|---|---|
| Receive POST/PUT/DELETE /api/articles | `Web` | Bootstrap | — | — |
| Validate JWT session | `Session` | Owns sessions | — | — |
| Store articles | `Article` | Owns article lifecycle | Needs persistence | — |
| Generate unique slugs | `Article` | Slug is article identity | — | — |
| Manage tags | `Article` (tagList) | Tags are article metadata | — | Separate concept overkill |
| Verify article ownership | `Article` | Author field on article | — | — |

## Concepts

| Concept | Owned state | Owned actions | Notes |
|---|---|---|---|
| `Web` | route table | `handle`, `respond` | Bootstrap |
| `Session` | token → userId | `lookup` | Auth validation |
| `Article` | slug, title, description, body, authorId, tagList, createdAt, updatedAt | `create`, `getBySlug`, `update`, `delete`, `authorCheck` | Core article concept |
| `Tag` | tag name → articles | `extract` (from article body) | Lightweight; tags extracted from articles |

## Coverage check

| Scenario | Concepts touched |
|---|---|
| `create-article` | `Web`, `Session`, `Article`, `Tag` |
| `update-article` | `Web`, `Session`, `Article` |
| `delete-article` | `Web`, `Session`, `Article` |
