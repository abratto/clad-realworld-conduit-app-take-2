sync WhenTagListListedThenWebRespondForListTags
## Rule
```
when { Tag/list: [] => [ outcome: "Listed" ; tags: ?tags ] }
where { A: bind (?tags as ?tags) ; C: bind (200 as ?status) }
then { Web/respond: [ status: ?status ; body: { tags: ?tags } ] }
```
