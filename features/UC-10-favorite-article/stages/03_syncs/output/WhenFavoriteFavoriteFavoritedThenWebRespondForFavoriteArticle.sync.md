## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|

sync WhenFavoriteFavoriteFavoritedThenWebRespondForFavoriteArticle
### Rule
```
when { Favorite/favorite: [ userId: ?_ ; articleId: ?_ ] => [ outcome: "Favorited" ] }
where { C: bind (200 as ?status) }
then { Web/respond: [ status: ?status ; body: { article: { favorited: true } } ] }
```
