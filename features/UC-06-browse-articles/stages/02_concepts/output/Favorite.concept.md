concept Favorite [UserId, ArticleId]
purpose to track which articles a user has favorited

## State
```
favorited: (UserId, ArticleId) -> Boolean
```

## Actions
```
isFavorited [ userId: UserId ; articleId: ArticleId ] => [ true | false ]
    checks if user favorited the article
    no state change
    flow token: { action: "Favorite.isFavorited", userId, articleId, outcome: "true" | "false" }
```
