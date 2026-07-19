sync WhenArticleCreateCreatedThenTagExtractForArticle

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 5 | 6 | `Article/create: [ title: ?_ ; description: ?_ ; body: ?_ ; tagList: ?_ ; authorId: ?_ ] => [ outcome: "Created" ; slug: ?_ ]` | `Tag/extract: [ tagList: ?tagList ]` | none |

## Rule

```
when { Article/create: [ title: ?_ ; description: ?_ ; body: ?_ ; tagList: ?_ ; authorId: ?_ ] => [ outcome: "Created" ; slug: ?_ ] }
where { D: Web: { ?flow :input [ :tagList ?tagList ] } }
then { Tag/extract: [ tagList: ?tagList ] }
```

## Cites

- `../01_usecase/output/usecase.md` — scenario "create-article", main flow step 6
