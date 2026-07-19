# Chain table — add-comment

| # | When | Then | Inputs | Outcome |
|---|---|---|---|---|
| 1 | `Web/request[POST /api/articles/:slug/comments]` | `Web.handle` | route, token, slug, body | `Routed(token, slug, body)` \| `Refused:noToken` |
| 2 | `Web.handle[Refused:noToken]` | `Web.respond[401]` | error | `Sent` |
| 3 | `Web.handle[Routed(token, slug, body)]` | `Session.lookup` | token | `FOUND(authorId)` \| `refused` |
| 4 | `Session.lookup[refused]` | `Web.respond[401]` | error | `Sent` |
| 5 | `Session.lookup[FOUND(authorId)]` | `Article.getBySlug` | slug | `FOUND` \| `refused` |
| 6 | `Article.getBySlug[refused]` | `Web.respond[404]` | error | `Sent` |
| 7 | `Article.getBySlug[FOUND(articleId)]` | `Comment.add` | articleId, authorId, body | `Added` \| `Refused:blankBody` |
| 8 | `Comment.add[Added(commentId)]` | `Web.respond[200]` | comment object with author | `Sent` |
| 9 | `Comment.add[Refused:blankBody]` | `Web.respond[422]` | error | `Sent` |

# Chain table — delete-comment

| # | When | Then | Inputs | Outcome |
|---|---|---|---|---|
| 1 | `Web/request[DELETE /api/articles/:slug/comments/:id]` | `Web.handle` | route, token, slug, commentId | `Routed(token, commentId)` |
| 2–6 | Same as add-comment rows 2–6 | | | |
| 7 | `Comment.authorCheck` | commentId, authorId | `IsAuthor` \| `NotAuthor` |
| 8 | `Comment.authorCheck[NotAuthor]` | `Web.respond[403]` | error | `Sent` |
| 9 | `Comment.authorCheck[IsAuthor]` | `Comment.delete` | commentId | `Deleted` |
| 10 | `Comment.delete[Deleted]` | `Web.respond[200]` | (ok) | `Sent` |
