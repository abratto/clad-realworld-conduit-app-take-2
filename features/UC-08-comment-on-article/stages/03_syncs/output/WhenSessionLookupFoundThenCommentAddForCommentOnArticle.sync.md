sync WhenSessionLookupFoundThenCommentAddForCommentOnArticle

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 5 | 7 | `Session/lookup: [ token: ?_ ] => [ outcome: "FOUND" ; userId: ?authorId ]` | `Comment/add: [ articleId: ?articleId ; authorId: ?authorId ; body: ?body ]` | none |

## Rule

```
when { Session/lookup: [ token: ?_ ] => [ outcome: "FOUND" ; userId: ?authorId ] }
where {
    A: bind (?authorId as ?authorId)
    D: Web: { ?_ :method "POST" ; :input [ :slug ?slug ; :body ?body ] }
    D: Article: { ?articleId :slug ?slug }
}
then { Comment/add: [ articleId: ?articleId ; authorId: ?authorId ; body: ?body ] }
```

## Cites

- `../01_usecase/output/usecase.md` — scenario "add-comment", main flow step 4
