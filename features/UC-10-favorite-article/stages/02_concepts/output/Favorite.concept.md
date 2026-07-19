concept Favorite [UserId, ArticleId]
purpose to manage article favorites
## Actions
```
favorite [ userId: UserId ; articleId: ArticleId ] => [ Favorited ]
    postcondition { favorite recorded }
    flow token: { action: "Favorite.favorite", userId, articleId, outcome: "Favorited" }

unfavorite [ userId: UserId ; articleId: ArticleId ] => [ Unfavorited ]
    postcondition { favorite removed }
    flow token: { action: "Favorite.unfavorite", userId, articleId, outcome: "Unfavorited" }
```
