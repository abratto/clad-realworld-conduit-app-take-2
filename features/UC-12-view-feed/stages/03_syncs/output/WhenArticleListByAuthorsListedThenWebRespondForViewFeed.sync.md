sync WhenArticleListByAuthorsListedThenWebRespondForViewFeed
| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 6 | 7 | `Article/listByAuthors: [ authorIds: ?_ ; limit: ?_ ; offset: ?_ ] => [ outcome: "Listed" ; articles: ?art ; count: ?cnt ]` | `Web/respond: [ status: 200 ; body: { articles: ?art, articlesCount: ?cnt } ]` | `200` |
## Rule
```
when { Article/listByAuthors: [ authorIds: ?_ ; limit: ?_ ; offset: ?_ ] => [ outcome: "Listed" ; articles: ?art ; count: ?cnt ] }
where { A: bind (?art as ?articles) ; A: bind (?cnt as ?count) ; C: bind (200 as ?status) }
then { Web/respond: [ status: ?status ; body: { articles: ?articles, articlesCount: ?count } ] }
```
