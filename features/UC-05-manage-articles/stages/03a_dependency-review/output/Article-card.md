# Dependency review — `Article`
## Section 1 — Invocations received
| Action | Flow (sync) | Data received | Pattern | Source |
|---|---|---|---|---|
| `create` | `WhenSessionLookupFoundThenArticleCreateForManageArticles` | title, description, body, tagList, authorId | A + D | trigger + Web input |
| `getBySlug` | `WhenSessionLookupFoundThenArticleGetBySlugForManageArticles` | slug | A + D | trigger + Web input |
| `authorCheck` | `WhenArticleGetBySlugFoundThenArticleAuthorCheckForManageArticles` | articleId, memberId | A | trigger token |
| `update` | `WhenArticleAuthorCheckIsAuthorThenArticleUpdateForManageArticles` | slug, fields | A + D | trigger + Web input |
| `delete` | `WhenArticleAuthorCheckIsAuthorThenArticleDeleteForManageArticles` | slug | A | trigger token |
## Section 2 — Inbound Pattern D: None
