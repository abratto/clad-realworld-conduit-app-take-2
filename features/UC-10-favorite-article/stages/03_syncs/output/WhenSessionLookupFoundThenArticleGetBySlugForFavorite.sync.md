sync WhenSessionLookupFoundThenArticleGetBySlugForFavorite
### Rule
```
when { Session/lookup: [ token: ?_ ] => [ outcome: "FOUND" ; userId: ?uid ] }
where { D: Web: { ?flow :input [ :slug ?slug ] } }
then { Article/getBySlug: [ slug: ?slug ] }
```
