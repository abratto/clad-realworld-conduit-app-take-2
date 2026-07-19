# Chain — view-comments
| # | When | Then | Outcome |
|---|---|---|---|
| 1 | `Web/request[GET /api/articles/:slug/comments]` | `Web.handle` | `Routed(slug)` |
| 2 | `Web.handle[Routed(slug)]` | `Article.getBySlug` | `FOUND` \| `refused` |
| 3 | `Article.getBySlug[refused]` | `Web.respond[404]` | `Sent` |
| 4 | `Article.getBySlug[FOUND]` | `Comment.listByArticle` | `Listed(comments)` |
| 5 | `Comment.listByArticle[Listed]` | `Web.respond[200]` | `Sent` |
