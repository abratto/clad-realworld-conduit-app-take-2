sync WhenSessionLookupFoundThenArticleCreateForArticle
## Sync Contract Matrix
| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 3 | 5 | `Session/lookup: [ token: ?_ ] => [ outcome: "FOUND" ; userId: ?authorId ]` | `Article/create: [ title: ?title ; description: ?description ; body: ?body ; tagList: ?tagList ; authorId: ?authorId ]` | none |
## Rule
```
when { Session/lookup: [ token: ?_ ] => [ outcome: "FOUND" ; userId: ?authorId ] }
where { A: bind (?authorId as ?authorId) ; D: Web: { ?flow :input [ :title ?title ; :description ?description ; :body ?body ; :tagList ?tagList ] } }
then { Article/create: [ title: ?title ; description: ?description ; body: ?body ; tagList: ?tagList ; authorId: ?authorId ] }
```
## Cites
- create-article main flow
