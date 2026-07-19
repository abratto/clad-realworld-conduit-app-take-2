# Chain table — read-article

| # | When | Then | Inputs | Outcome |
|---|---|---|---|---|
| 1 | `Web/request[GET /api/articles/:slug]` | `Web.handle` | route, slug, token? | `Routed(slug, token?)` |
| 2 | `Web.handle[Routed(slug)]` | `Article.getBySlug` | slug | `FOUND` \| `refused` |
| 3 | `Article.getBySlug[refused]` | `Web.respond[404]` | error | `Sent` |
| 4 | `Article.getBySlug[FOUND]` | (enrich with author, favorites) | — | — |
| 5 | (enriched) | `Web.respond[200]` | `{article: {slug, title, ..., author}}` | `Sent` |
