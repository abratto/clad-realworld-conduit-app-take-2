sync WhenCommentAddRefusedBlankBodyThenWebRespondForComment

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 7 | 9 | `Comment/add: [ articleId: ?_ ; authorId: ?_ ; body: ?_ ] => [ refused: "blankBody" ]` | `Web/respond: [ status: 422 ; body: { errors: { body: ["can't be blank"] } } ]` | `422`, `"can't be blank"` |

## Rule

```
when { Comment/add: [ articleId: ?_ ; authorId: ?_ ; body: ?_ ] => [ refused: "blankBody" ] }
where { C: bind (422 as ?status) ; C: bind ({"errors":{"body":["can't be blank"]}} as ?body) }
then { Web/respond: [ status: ?status ; body: ?body ] }
```

## Cites

- `../01_usecase/output/usecase.md` — scenario "add-comment", extension "blank body"
