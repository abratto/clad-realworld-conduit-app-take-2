# Dependency review — `Article`
## Section 1 — Invocations received
| Action | Flow (sync) | Data received | Pattern | Source |
|---|---|---|---|---|
| `create` | `WhenSessionLookupFoundThenArticleCreateForArticle` | title, description, body, tagList, authorId | A + D | trigger + Web input |
| `getBySlug` | `WhenSessionLookupFoundThenArticleGetBySlugForArticle` | slug | A + D | trigger + Web input |
| `authorCheck` | `WhenArticleGetBySlugFoundThenArticleAuthorCheckForArticle` | articleId, memberId | A | trigger token |
| `update` | `WhenArticleAuthorCheckIsAuthorThenArticleUpdateForArticle` | slug, fields | A + D | trigger + Web input |
| `delete` | `WhenArticleAuthorCheckIsAuthorThenArticleDeleteForArticle` | slug | A | trigger token |
## Section 2 — Inbound Pattern D: None
