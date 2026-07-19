concept Comment [CommentId, ArticleId]
purpose to list comments for an article
## Actions
```
listByArticle [ articleId: ArticleId ] => [ Listed(comments) ]
    no state change
    flow token: { action: "Comment.listByArticle", articleId, outcome: "Listed" }
```
