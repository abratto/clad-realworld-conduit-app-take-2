concept Comment [CommentId, ArticleId, UserId]
purpose to manage comments on articles

## State
```
body: CommentId -> String; authorId: CommentId -> UserId; articleId: CommentId -> ArticleId
createdAt, updatedAt: CommentId -> DateTime
```

## Actions
```
add [ articleId: ArticleId ; authorId: UserId ; body: String ] => [ commentId: CommentId ]
    precondition { body not blank }
    postcondition { comment created }
    flow token: { action: "Comment.add", articleId, authorId, outcome: "Added" }

delete [ commentId: CommentId ] => [ Deleted ]
    precondition { comment exists }
    postcondition { comment removed }
    flow token: { action: "Comment.delete", commentId, outcome: "Deleted" }

authorCheck [ commentId: CommentId ; userId: UserId ] => [ IsAuthor | NotAuthor ]
    no state change
    flow token: { action: "Comment.authorCheck", commentId, userId, outcome: "IsAuthor" }
```

## Notes
- Implemented in CommentConcept.java at com.conduit.app.concepts.comment.
