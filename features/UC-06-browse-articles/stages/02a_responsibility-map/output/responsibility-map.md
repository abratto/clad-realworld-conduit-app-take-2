# Responsibility map — Browse Articles

## Derivation rubric

| Responsibility | Candidate concept | Why separate | Not bootstrap? |
|---|---|---|---|
| Receive GET /api/articles with filters | `Web` | Bootstrap | — |
| List articles with filters (tag, author, favorited) | `Article` | Owns article data | Needs persistence |
| Pagination + sort | `Article` | Part of article listing | — |
| Resolve author profile for each article | `User` | Owns user profiles | — |
| Check favorited status (if authenticated) | `Favorite` | Owns favorite relationships | Separate lifecycle |

## Concepts

| Concept | Owned state | Owned actions | Notes |
|---|---|---|---|
| `Web` | route table | `handle`, `respond` | Bootstrap |
| `Article` | slug, title, desc, body, authorId, tagList, createdAt | `list` | New action: `list(filters, limit, offset)` returns paginated articles |
| `User` | username, bio, image | `lookupByUsername`, `getProfile` | Resolve author profiles |
| `Favorite` | userId → articleId | `isFavorited` | New action: check if reader favorited |

## Coverage check

| Scenario | Concepts touched |
|---|---|
| `browse-articles` | `Web`, `Article`, `User`, `Favorite` |
