sync WhenCommentAuthorCheckNotAuthorThenWebRespondForCommentOnArticle

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 7 | 8 | `Comment/authorCheck: [ commentId: ?_ ; userId: ?_ ] => [ outcome: "NotAuthor" ]` | `Web/respond: [ status: 403 ; body: { errors: { comment: ["forbidden"] } } ]` | `403`, `"forbidden"` |

## Rule

```
when { Comment/authorCheck: [ commentId: ?_ ; userId: ?_ ] => [ outcome: "NotAuthor" ] }
where { C: bind (403 as ?status) ; C: bind ({"errors":{"comment":["forbidden"]}} as ?body) }
then { Web/respond: [ status: ?status ; body: ?body ] }
```

## Cites

- `../01_usecase/output/usecase.md` — scenario "delete-comment", extension "not author"
