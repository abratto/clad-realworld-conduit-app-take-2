# Pattern D summary — UC-05

## Pattern D reads
| Consumer (sync) | Field read | Owner concept | Key | In flow |
|---|---|---|---|---|
| `WhenSessionLookupFoundThenArticleCreateForArticle` | title, description, body, tagList | Web (request input) | `?flow` | create-article |
| `WhenSessionLookupFoundThenArticleGetBySlugForArticle` | slug | Web (request input) | `?flow` | update-article, delete-article |
