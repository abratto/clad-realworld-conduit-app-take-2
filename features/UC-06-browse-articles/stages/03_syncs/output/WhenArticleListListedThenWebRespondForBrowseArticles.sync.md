sync WhenArticleListListedThenWebRespondForBrowseArticles
## Sync Contract Matrix
| 2 | 4 | `Article/list: [ limit: ?limit ; offset: ?offset ] => [ outcome: "Listed" ; articles: ?articles ; count: ?count ]` | `Web/respond: [ status: 200 ; body: { articles: ?articles, articlesCount: ?count } ]` | `200` |
## Rule
```
when { Article/list: [ limit: ?_ ; offset: ?_ ] => [ outcome: "Listed" ; articles: ?articles ; count: ?count ] }
where { A: bind (?articles as ?articles) ; A: bind (?count as ?count) ; C: bind (200 as ?status) }
then { Web/respond: [ status: ?status ; body: { articles: ?articles, articlesCount: ?count } ] }
```
## Cites
- browse-articles main flow
