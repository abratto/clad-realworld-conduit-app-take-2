sync WhenWebHandleRoutedThenTagListForListTags
## Rule
```
when { Web/handle: [ route: ?_ ; body: ?_ ] => [ routed: ?_ ] }
then { Tag/list: [] }
```
