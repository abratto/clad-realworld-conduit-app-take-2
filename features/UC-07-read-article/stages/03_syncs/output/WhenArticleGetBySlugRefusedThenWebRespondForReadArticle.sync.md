sync WhenArticleGetBySlugRefusedThenWebRespondForReadArticle
## Sync Contract Matrix
| 2 | 3 | `Article/getBySlug: [ slug: ?slug ] => [ refused ]` | `Web/respond: [ status: 404 ; body: { errors: { article: ["not found"] } } ]` | `404` |
## Rule
```
when { Article/getBySlug: [ slug: ?slug ] => [ refused ] }
where { C: bind (404 as ?status) ; C: bind ({"errors":{"article":["not found"]}} as ?body) }
then { Web/respond: [ status: ?status ; body: ?body ] }
```
