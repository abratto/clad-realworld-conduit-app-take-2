sync WhenArticleUpdateBlankTitleThenWebRespondForManageArticles

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 9 | 10 | `Article/update: [ slug: ?_ ; title?: ?_ ; description?: ?_ ; body?: ?_ ] => [ refused: "blankTitle" ]` | `Web/respond: [ status: 422 ; body: { errors: { title: ["can't be blank"] } } ]` | `422`, `"can't be blank"` |

## Rule

```
when { Article/update: [ slug: ?_ ; title?: ?_ ; description?: ?_ ; body?: ?_ ] => [ refused: "blankTitle" ] }
where { C: bind (422 as ?status) ; C: bind ({"errors":{"title":["can't be blank"]}} as ?body) }
then { Web/respond: [ status: ?status ; body: ?body ] }
```

## Cites

- `../01_usecase/output/usecase.md` — scenario "update-article", extension "blank title"
