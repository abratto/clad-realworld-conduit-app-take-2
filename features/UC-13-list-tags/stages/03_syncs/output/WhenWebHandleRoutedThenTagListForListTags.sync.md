## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|

sync WhenWebHandleRoutedThenTagListForListTags
## Rule
```
when { Web/handle: [ route: ?_ ; body: ?_ ] => [ routed: ?_ ] }
then { Tag/list: [] }
```
