# Favorite — SPEC

## Actions

### `isFavorited(userId: UserId, articleId: ArticleId) -> true | false`
- **Inputs:** `userId: UserId`, `articleId: ArticleId`
- **Outcomes (enum):** `true`, `false`
- **Flow token:** `Favorite.isFavorited { userId, articleId, outcome }`
