sync WhenCommentListByArticleListedThenWebRespondForViewComments
## Rule
```
when { Comment/listByArticle: [ articleId: ?id ] => [ outcome: "Listed" ] }
where { C: bind (200 as ?status) }
then { Web/respond: [ status: ?status ; body: { comments: [] } ] }
```
