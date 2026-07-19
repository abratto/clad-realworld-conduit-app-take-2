# Comment — SPEC
## Actions
### `add(articleId: ArticleId, authorId: UserId, body: String) -> Added | refused`
- **Outcomes (enum):** `Added`, `refused`
### `delete(commentId: CommentId) -> Deleted | refused`
- **Outcomes (enum):** `Deleted`, `refused`
### `authorCheck(commentId: CommentId, userId: UserId) -> IsAuthor | NotAuthor`
- **Outcomes (enum):** `IsAuthor`, `NotAuthor`
