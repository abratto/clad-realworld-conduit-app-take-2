# Chain — list-tags
| # | When | Then | Outcome |
|---|---|---|---|
| 1 | `Web/request[GET /api/tags]` | `Web.handle` | `Routed` |
| 2 | `Web.handle[Routed]` | `Tag.list` | `Listed(tags)` |
| 3 | `Tag.list[Listed]` | `Web.respond[200]` | `Sent` |
