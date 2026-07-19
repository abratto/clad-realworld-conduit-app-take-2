sync WhenTagExtractExtractedThenWebRespondForManageArticles

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 6 | 8 | `Tag/extract: [ tagList: ?_ ] => [ outcome: "Extracted" ]` | `Web/respond: [ status: 201 ; body: { article: { slug, title, description, body, tagList, createdAt, updatedAt, author } } ]` | `201` |

## Rule

```
when { Tag/extract: [ tagList: ?_ ] => [ outcome: "Extracted" ] }
where {
    C: bind (201 as ?status)
    B: bind (?slug as ?slug)
    D: Article: { ?articleId :slug ?slug ; :title ?title ; :description ?description ; :body ?body ; :tagList ?articleTagList ; :createdAt ?createdAt ; :updatedAt ?updatedAt ; :authorId ?authorId }
    D: User: { ?user :userId ?authorId ; :username ?username ; :bio ?bio ; :image ?image }
}
then { Web/respond: [ status: ?status ; body: { article: { slug: ?slug, title: ?title, description: ?description, body: ?body, tagList: ?articleTagList, createdAt: ?createdAt, updatedAt: ?updatedAt, author: { username: ?username, bio: ?bio, image: ?image } } } ] }
```

## Cites

- `../01_usecase/output/usecase.md` — scenario "create-article", main flow step 7
