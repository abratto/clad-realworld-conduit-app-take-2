# Article — SPEC

## Actions

### `list(tag: String?, author: String?, favorited: String?, limit: Int, offset: Int) -> Listed`
- **Inputs:** `tag: String?`, `author: String?`, `favorited: String?`, `limit: Int`, `offset: Int`
- **Outcomes (enum):** `Listed`
- **Flow token:** `Article.list { filters, limit, offset, outcome, articles, count }`

## Response shapes

### `GET /api/articles`
- **Success wrapper:** `{"articles": [...], "articlesCount": N}`
- **Required fields (200):**
  - `$.articles[*].slug` — `String`
  - `$.articles[*].title` — `String`
  - `$.articles[*].description` — `String`
  - `$.articles[*].tagList` — `[String]`
  - `$.articles[*].createdAt` — `DateTime`
  - `$.articles[*].updatedAt` — `DateTime`
  - `$.articles[*].favorited` — `Boolean`
  - `$.articles[*].favoritesCount` — `Int`
  - `$.articles[*].author.username` — `String`
  - `$.articlesCount` — `Int`
