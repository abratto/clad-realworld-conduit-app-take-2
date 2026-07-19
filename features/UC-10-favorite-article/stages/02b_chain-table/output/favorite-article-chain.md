# Chain — favorite/unfavorite article

## favorite
| # | When | Then | Outcome |
|---|---|---|---|
| 1 | `Web/request[POST /api/articles/:slug/favorite]` | `Web.handle` | `Routed(token, slug)` |
| 2 | `Web.handle[Routed]` | `Session.lookup` | `FOUND(userId)` \| `refused` |
| 3 | `Session.lookup[FOUND]` | `Article.getBySlug` | `FOUND(articleId)` \| `refused` |
| 4 | `Article.getBySlug[FOUND]` | `Favorite.favorite` | `Favorited` |
| 5 | `Favorite.favorite[Favorited]` | `Web.respond[200]` | `Sent` |

## unfavorite
| 1 | `Web/request[DELETE /api/articles/:slug/favorite]` | `Web.handle` | `Routed(token, slug)` |
| 2-3 | Same as above | | |
| 4 | `Article.getBySlug[FOUND]` | `Favorite.unfavorite` | `Unfavorited` |
| 5 | `Favorite.unfavorite[Unfavorited]` | `Web.respond[200]` | `Sent` |
