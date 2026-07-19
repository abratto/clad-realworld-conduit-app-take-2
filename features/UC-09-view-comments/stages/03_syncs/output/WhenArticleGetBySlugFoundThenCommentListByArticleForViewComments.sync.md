sync WhenArticleGetBySlugFoundThenCommentListByArticleForViewComments
## Rule
```
when { Article/getBySlug: [ slug: ?slug ] => [ outcome: "FOUND" ; articleId: ?id ] }
where { A: bind (?id as ?articleId) }
then { Comment/listByArticle: [ articleId: ?articleId ] }
```
