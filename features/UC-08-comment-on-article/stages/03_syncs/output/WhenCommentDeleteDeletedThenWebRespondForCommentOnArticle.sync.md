sync WhenCommentDeleteDeletedThenWebRespondForCommentOnArticle

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 9 | 10 | `Comment/delete: [ commentId: ?_ ] => [ outcome: "Deleted" ]` | `Web/respond: [ status: 200 ]` | `200` |

## Rule

```
when { Comment/delete: [ commentId: ?_ ] => [ outcome: "Deleted" ] }
where { C: bind (200 as ?status) }
then { Web/respond: [ status: ?status ] }
```

## Cites

- `../01_usecase/output/usecase.md` — scenario "delete-comment", main flow step 5
