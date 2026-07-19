sync WhenArticleAuthorCheckNotAuthorThenWebRespondForManageArticles

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 7 | 8 | `Article/authorCheck: [ articleId: ?_ ; memberId: ?_ ] => [ outcome: "NotAuthor" ]` | `Web/respond: [ status: 403 ; body: { errors: { article: ["forbidden"] } } ]` | `403`, `"forbidden"` |

## Rule

```
when { Article/authorCheck: [ articleId: ?_ ; memberId: ?_ ] => [ outcome: "NotAuthor" ] }
where { C: bind (403 as ?status) ; C: bind ({"errors":{"article":["forbidden"]}} as ?body) }
then { Web/respond: [ status: ?status ; body: ?body ] }
```

## Cites

- `../01_usecase/output/usecase.md` — scenarios "update-article", "delete-article", extension "not author"
