sync WhenArticleGetBySlugRefusedThenWebRespondForArticle

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 5 | 6 | `Article/getBySlug: [ slug: ?_ ] => [ refused ]` | `Web/respond: [ status: 404 ; body: { errors: { article: ["not found"] } } ]` | `404`, `"not found"` |

## Rule

```
when { Article/getBySlug: [ slug: ?_ ] => [ refused ] }
where { C: bind (404 as ?status) ; C: bind ({"errors":{"article":["not found"]}} as ?body) }
then { Web/respond: [ status: ?status ; body: ?body ] }
```

## Cites

- `../01_usecase/output/usecase.md` — scenarios "update-article", "delete-article", extension "article not found"
