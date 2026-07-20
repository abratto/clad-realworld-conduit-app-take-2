## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|

sync WhenSessionLookupFoundThenArticleGetBySlugForFavoriteArticle
### Rule
```
when { Session/lookup: [ token: ?_ ] => [ outcome: "FOUND" ; userId: ?uid ] }
where { D: Web: { ?flow :input [ :slug ?slug ] } }
then { Article/getBySlug: [ slug: ?slug ] }
```
