sync WhenWebHandleRoutedThenArticleGetBySlugForReadArticle
## Sync Contract Matrix
| 1 | 2 | `Web/handle: [ route ; body ] => [ routed: ?slug ]` | `Article/getBySlug: [ slug: ?slug ]` | none |
## Rule
```
when { Web/handle: [ route: ?_ ; body: ?_ ] => [ routed: ?slug ] }
where { A: bind (?slug as ?slug) }
then { Article/getBySlug: [ slug: ?slug ] }
```
