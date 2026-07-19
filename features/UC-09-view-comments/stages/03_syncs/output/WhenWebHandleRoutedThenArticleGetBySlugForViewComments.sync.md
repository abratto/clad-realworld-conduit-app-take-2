sync WhenWebHandleRoutedThenArticleGetBySlugForViewComments
## Rule
```
when { Web/handle: [ route: ?_ ; body: ?_ ] => [ routed: ?slug ] }
where { A: bind (?slug as ?slug) }
then { Article/getBySlug: [ slug: ?slug ] }
```
