sync WhenArticleCreateRefusedBlankTitleThenWebRespondForArticle

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 5 | 7 | `Article/create: [ title: ?_ ; description: ?_ ; body: ?_ ; tagList: ?_ ; authorId: ?_ ] => [ refused: "blankTitle" ]` | `Web/respond: [ status: 422 ; body: { errors: { title: ["can't be blank"] } } ]` | `422`, `"can't be blank"` |

## Rule

```
when { Article/create: [ title: ?_ ; description: ?_ ; body: ?_ ; tagList: ?_ ; authorId: ?_ ] => [ refused: "blankTitle" ] }
where { C: bind (422 as ?status) ; C: bind ({"errors":{"title":["can't be blank"]}} as ?body) }
then { Web/respond: [ status: ?status ; body: ?body ] }
```

## Cites

- `../01_usecase/output/usecase.md` — scenario "create-article", extension "blank title"
