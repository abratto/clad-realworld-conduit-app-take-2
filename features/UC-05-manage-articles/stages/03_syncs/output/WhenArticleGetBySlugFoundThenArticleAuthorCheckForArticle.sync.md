sync WhenArticleGetBySlugFoundThenArticleAuthorCheckForArticle

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 5 | 7 | `Article/getBySlug: [ slug: ?_ ] => [ outcome: "FOUND" ; articleId: ?articleId ; authorId: ?authorId ; title: ?_ ; description: ?_ ; body: ?_ ; tagList: ?_ ; createdAt: ?_ ; updatedAt: ?_ ]` | `Article/authorCheck: [ articleId: ?articleId ; memberId: ?memberId ]` | none |

## Rule

```
when { Article/getBySlug: [ slug: ?_ ] => [ outcome: "FOUND" ; articleId: ?articleId ; authorId: ?authorId ; title: ?_ ; description: ?_ ; body: ?_ ; tagList: ?_ ; createdAt: ?_ ; updatedAt: ?_ ] }
where {
    A: bind (?articleId as ?articleId)
    B: bind (?memberId as ?memberId)
}
then { Article/authorCheck: [ articleId: ?articleId ; memberId: ?memberId ] }
```

## Cites

- `../01_usecase/output/usecase.md` — scenarios "update-article", "delete-article", main flow step 4
