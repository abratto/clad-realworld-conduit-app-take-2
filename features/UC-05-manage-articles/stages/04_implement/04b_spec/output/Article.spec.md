# Article — SPEC

## Actions

### `create(title: String, description: String, body: String, tagList: [String], authorId: UserId) -> Created`
- **Inputs:** `title: String`, `description: String`, `body: String`, `tagList: [String]`, `authorId: UserId`
- **Outcomes (enum):** `Created`, `refused`
- **Flow token:** `Article.create { title, authorId, slug, outcome }`

### `getBySlug(slug: String) -> FOUND | refused`
- **Inputs:** `slug: String`
- **Outcomes (enum):** `FOUND`, `refused`
- **Flow token:** `Article.getBySlug { slug, articleId, authorId, outcome }`

### `update(slug: String, title?: String, description?: String, body?: String) -> Updated | refused`
- **Inputs:** `slug: String`, `title?: String`, `description?: String`, `body?: String`
- **Outcomes (enum):** `Updated`, `refused`
- **Flow token:** `Article.update { slug, outcome }`

### `delete(slug: String) -> Deleted | refused`
- **Inputs:** `slug: String`
- **Outcomes (enum):** `Deleted`, `refused`
- **Flow token:** `Article.delete { slug, outcome }`

### `authorCheck(articleId: ArticleId, memberId: UserId) -> IsAuthor | NotAuthor`
- **Inputs:** `articleId: ArticleId`, `memberId: UserId`
- **Outcomes (enum):** `IsAuthor`, `NotAuthor`
- **Flow token:** `Article.authorCheck { articleId, memberId, outcome }`
