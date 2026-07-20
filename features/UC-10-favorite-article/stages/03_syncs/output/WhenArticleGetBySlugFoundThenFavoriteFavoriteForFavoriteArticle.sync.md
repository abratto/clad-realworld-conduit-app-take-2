## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|

sync WhenArticleGetBySlugFoundThenFavoriteFavoriteForFavoriteArticle
### Rule
```
when { Article/getBySlug: [ slug: ?slug ] => [ outcome: "FOUND" ; articleId: ?id ] }
where { A: bind (?id as ?articleId) ; B: bind (?uid as ?userId) }
then { Favorite/favorite: [ userId: ?userId ; articleId: ?articleId ] }
```
