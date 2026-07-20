## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|

sync WhenArticleGetBySlugFoundThenCommentListByArticleForViewComments
## Rule
```
when { Article/getBySlug: [ slug: ?slug ] => [ outcome: "FOUND" ; articleId: ?id ] }
where { A: bind (?id as ?articleId) }
then { Comment/listByArticle: [ articleId: ?articleId ] }
```
