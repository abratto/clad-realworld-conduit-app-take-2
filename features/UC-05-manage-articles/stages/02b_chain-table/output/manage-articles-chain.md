# Chain table — create-article

## Scenario

`create-article` — Member submits a new article with title, description, body, and optional tagList.

## Chain

| # | When | Then | Inputs | Outcome | Why this step |
|---|---|---|---|---|---|
| 1 | `Web/request[POST /api/articles]` | `Web.handle` | route, token, body `{title, description, body, tagList}` | `Routed(token, title, description, body, tagList)` \| `Refused:noToken` | HTTP entry; validates token present. |
| 2 | `Web.handle[Refused:noToken]` | `Web.respond[401]` | `{errors: {token: ["is missing"]}}` | `Sent` | No token. |
| 3 | `Web.handle[Routed(token, title, ...)]` | `Session.lookup` | token | `FOUND(authorId)` \| `refused` | Validate session. |
| 4 | `Session.lookup[refused]` | `Web.respond[401]` | `{errors: {token: ["is invalid"]}}` | `Sent` | Bad token. |
| 5 | `Session.lookup[FOUND(authorId)]` | `Article.create` | title, description, body, tagList, authorId | `Created(slug)` \| `Refused:blankTitle` | Create article with generated slug. |
| 6 | `Article.create[Created(slug)]` | `Tag.extract` | tagList | `Extracted` | Store tags from article. |
| 7 | `Article.create[Refused:blankTitle]` | `Web.respond[422]` | `{errors: {title: ["can't be blank"]}}` | `Sent` | Blank title. |
| 8 | `Tag.extract[Extracted]` | `Web.respond[201]` | `{article: {slug, title, description, body, tagList, createdAt, updatedAt, author}}` | `Sent` | Article created. |

# Chain table — update-article

| # | When | Then | Inputs | Outcome |
|---|---|---|---|---|
| 1 | `Web/request[PUT /api/articles/:slug]` | `Web.handle` | route, token, slug, body `{title?, description?, body?}` | `Routed(token, slug, fields)` \| `Refused:noToken` |
| 2 | `Web.handle[Refused:noToken]` | `Web.respond[401]` | error | `Sent` |
| 3 | `Web.handle[Routed(token, slug, body)]` | `Session.lookup` | token | `FOUND(memberId)` \| `refused` |
| 4 | `Session.lookup[refused]` | `Web.respond[401]` | error | `Sent` |
| 5 | `Session.lookup[FOUND(memberId)]` | `Article.getBySlug` | slug | `FOUND(article)` \| `refused` |
| 6 | `Article.getBySlug[refused]` | `Web.respond[404]` | `{errors: {article: ["not found"]}}` | `Sent` |
| 7 | `Article.getBySlug[FOUND(authorId, ...)]` | `Article.authorCheck` | articleId, memberId | `IsAuthor` \| `NotAuthor` |
| 8 | `Article.authorCheck[NotAuthor]` | `Web.respond[403]` | `{errors: {article: ["forbidden"]}}` | `Sent` |
| 9 | `Article.authorCheck[IsAuthor]` | `Article.update` | slug, fields | `Updated` \| `Refused:blankTitle` |
| 10 | `Article.update[Refused:blankTitle]` | `Web.respond[422]` | error | `Sent` |
| 11 | `Article.update[Updated]` | `Web.respond[200]` | article object | `Sent` |

# Chain table — delete-article

| # | When | Then | Inputs | Outcome |
|---|---|---|---|---|
| 1 | `Web/request[DELETE /api/articles/:slug]` | `Web.handle` | route, token, slug | `Routed(token, slug)` \| `Refused:noToken` |
| 2–6 | Same as update-article rows 2–7 (auth, lookup, refusals) | | | |
| 7 | `Article.authorCheck[IsAuthor]` | `Article.delete` | slug | `Deleted` |
| 8 | `Article.delete[Deleted]` | `Web.respond[204]` | (no body) | `Sent` |

## Cross-checks

- Every concept (`Web`, `Session`, `Article`, `Tag`) is in the responsibility map.
- First row is `Web/request → Web.handle` (R4); last rows are `... → Web.respond[...]`.
- Auth is checked before any article operation.
- Author-only enforcement via `Article.authorCheck`.
