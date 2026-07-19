sync WhenArticleGetBySlugRefusedThenWebRespondForViewComments
## Rule
```
when { Article/getBySlug: [ slug: ?slug ] => [ refused ] }
where { C: bind (404 as ?status) ; C: bind ({"errors":{"article":["not found"]}} as ?body) }
then { Web/respond: [ status: ?status ; body: ?body ] }
```
