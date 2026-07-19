sync WhenArticleAuthorCheckIsAuthorThenArticleUpdateForArticle

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 7 | 9 | `Article/authorCheck: [ articleId: ?_ ; memberId: ?_ ] => [ outcome: "IsAuthor" ]` | `Article/update: [ slug: ?slug ; title: ?title ; description: ?description ; body: ?body ]` | none |

## Rule

```
when { Article/authorCheck: [ articleId: ?_ ; memberId: ?_ ] => [ outcome: "IsAuthor" ] }
where {
    B: bind (?slug as ?slug)
    D: Web: { ?flow :input [ :title ?title ; :description ?description ; :body ?body ] }
}
then { Article/update: [ slug: ?slug ; title: ?title ; description: ?description ; body: ?body ] }
```

## Cites

- `../01_usecase/output/usecase.md` — scenario "update-article", main flow step 5
