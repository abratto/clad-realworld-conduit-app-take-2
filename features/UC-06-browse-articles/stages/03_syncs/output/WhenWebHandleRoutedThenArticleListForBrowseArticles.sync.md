sync WhenWebHandleRoutedThenArticleListForBrowseArticles
## Sync Contract Matrix
| 1 | 2 | `Web/handle: [ route ; body ] => [ routed: ?filters, ?token? ]` | `Article/list: [ tag: ?tag ; author: ?author ; favorited: ?fav ; limit: ?limit ; offset: ?offset ]` | none |
## Rule
```
when { Web/handle: [ route: ?_ ; body: ?_ ] => [ routed: ?_token, ?_tag, ?_author, ?_fav, ?_limit, ?_offset ] }
where { A: bind (?_limit as ?limit) ; A: bind (?_offset as ?offset) }
then { Article/list: [ limit: ?limit ; offset: ?offset ] }
```
## Cites
- browse-articles main flow
