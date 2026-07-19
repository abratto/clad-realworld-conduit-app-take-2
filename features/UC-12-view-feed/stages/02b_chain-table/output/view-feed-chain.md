# Chain table — view-feed

| # | When | Then | Inputs | Outcome | Why |
|---|---|---|---|---|---|
| 1 | `Web/request[GET /api/articles/feed]` | `Web.handle` | route, token, limit, offset | `Routed(token, limit, offset)` \| `Refused:noToken` | HTTP entry. Token required. |
| 2 | `Web.handle[Refused:noToken]` | `Web.respond[401]` | error | `Sent` | No token. |
| 3 | `Web.handle[Routed(token, limit, offset)]` | `Session.lookup` | token | `FOUND(userId)` \| `refused` | Validate session. |
| 4 | `Session.lookup[refused]` | `Web.respond[401]` | error | `Sent` | Invalid token. |
| 5 | `Session.lookup[FOUND(userId)]` | `Follow.getFollowedUsers` | userId | `Listed(followeeIds)` | Get list of followed users. |
| 6 | `Follow.getFollowedUsers[Listed(followeeIds)]` | `Article.listByAuthors` | authorIds, limit, offset | `Listed(articles, count)` | Get articles from followees. |
| 7 | `Article.listByAuthors[Listed]` | `Web.respond[200]` | articles, count | `Sent` | Return paginated feed. |
