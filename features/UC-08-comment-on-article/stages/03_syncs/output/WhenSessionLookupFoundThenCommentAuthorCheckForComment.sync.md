sync WhenSessionLookupFoundThenCommentAuthorCheckForComment

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 5 | 7 | `Session/lookup: [ token: ?_ ] => [ outcome: "FOUND" ; userId: ?memberId ]` | `Comment/authorCheck: [ commentId: ?commentId ; userId: ?userId ]` | none |

## Rule

```
when { Session/lookup: [ token: ?_ ] => [ outcome: "FOUND" ; userId: ?memberId ] }
where {
    A: bind (?memberId as ?userId)
    D: Web: { ?_ :method "DELETE" ; :input [ :commentId ?commentId ] }
}
then { Comment/authorCheck: [ commentId: ?commentId ; userId: ?userId ] }
```

## Cites

- `../01_usecase/output/usecase.md` — scenario "delete-comment", main flow step 3
