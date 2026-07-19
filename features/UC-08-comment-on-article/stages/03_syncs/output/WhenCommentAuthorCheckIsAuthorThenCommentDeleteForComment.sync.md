sync WhenCommentAuthorCheckIsAuthorThenCommentDeleteForComment

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 7 | 9 | `Comment/authorCheck: [ commentId: ?_ ; userId: ?_ ] => [ outcome: "IsAuthor" ]` | `Comment/delete: [ commentId: ?commentId ]` | none |

## Rule

```
when { Comment/authorCheck: [ commentId: ?_ ; userId: ?_ ] => [ outcome: "IsAuthor" ] }
where { B: bind (?commentId as ?commentId) }
then { Comment/delete: [ commentId: ?commentId ] }
```

## Cites

- `../01_usecase/output/usecase.md` — scenario "delete-comment", main flow step 4
