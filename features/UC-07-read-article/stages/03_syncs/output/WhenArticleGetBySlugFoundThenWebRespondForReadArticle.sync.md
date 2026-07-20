sync WhenArticleGetBySlugFoundThenWebRespondForReadArticle
| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 2 | 5 | `Article/getBySlug: [ slug: ?slug ] => [ outcome: "FOUND" ; articleId: ?id ; title: ?t ; description: ?d ; body: ?b ]` | `Web/respond: [ status: 200 ; body: { article: { slug: ?slug, title: ?t, description: ?d, body: ?b } } ]` | `200` |
## Rule
```
when { Article/getBySlug: [ slug: ?slug ] => [ outcome: "FOUND" ] }
where { C: bind (200 as ?status) }
then { Web/respond: [ status: ?status ; body: { article: { slug: ?slug } } ] }
```
