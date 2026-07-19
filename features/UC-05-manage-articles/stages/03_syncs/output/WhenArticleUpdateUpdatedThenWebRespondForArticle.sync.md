sync WhenArticleUpdateUpdatedThenWebRespondForArticle

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 9 | 11 | `Article/update: [ slug: ?_ ; title?: ?_ ; description?: ?_ ; body?: ?_ ] => [ outcome: "Updated" ]` | `Web/respond: [ status: 200 ; body: { article: { slug, title, description, body, tagList, createdAt, updatedAt, author } } ]` | `200` |

## Rule

```
when { Article/update: [ slug: ?_ ; title?: ?_ ; description?: ?_ ; body?: ?_ ] => [ outcome: "Updated" ] }
where {
    C: bind (200 as ?status)
    B: bind (?slug as ?slug)
    D: Article: { ?articleId :slug ?slug ; :title ?title ; :description ?description ; :body ?body ; :tagList ?articleTagList ; :createdAt ?createdAt ; :updatedAt ?updatedAt ; :authorId ?authorId }
    D: User: { ?user :userId ?authorId ; :username ?username ; :bio ?bio ; :image ?image }
}
then { Web/respond: [ status: ?status ; body: { article: { slug: ?slug, title: ?title, description: ?description, body: ?body, tagList: ?articleTagList, createdAt: ?createdAt, updatedAt: ?updatedAt, author: { username: ?username, bio: ?bio, image: ?image } } } ] }
```

## Cites

- `../01_usecase/output/usecase.md` — scenario "update-article", main flow step 6
