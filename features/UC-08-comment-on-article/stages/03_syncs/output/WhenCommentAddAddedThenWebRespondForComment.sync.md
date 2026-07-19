sync WhenCommentAddAddedThenWebRespondForComment

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 7 | 8 | `Comment/add: [ articleId: ?_ ; authorId: ?_ ; body: ?_ ] => [ outcome: "Added" ; commentId: ?commentId ]` | `Web/respond: [ status: 200 ; body: { comment: { id, createdAt, updatedAt, body, author } } ]` | `200` |

## Rule

```
when { Comment/add: [ articleId: ?_ ; authorId: ?_ ; body: ?_ ] => [ outcome: "Added" ; commentId: ?commentId ] }
where {
    C: bind (200 as ?status)
    B: bind (?commentId as ?commentId)
    D: Comment: { ?commentId :body ?body ; :createdAt ?createdAt ; :updatedAt ?updatedAt ; :authorId ?authorId }
    D: User: { ?user :userId ?authorId ; :username ?username ; :bio ?bio ; :image ?image }
}
then { Web/respond: [ status: ?status ; body: { comment: { id: ?commentId, createdAt: ?createdAt, updatedAt: ?updatedAt, body: ?body, author: { username: ?username, bio: ?bio, image: ?image } } } ] }
```

## Cites

- `../01_usecase/output/usecase.md` — scenario "add-comment", main flow step 5
