## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|

sync WhenWebHandleRoutedThenSessionLookupForFavoriteArticle
### Rule
```
when { Web/handle: [ route: ?_ ; body: ?_ ] => [ routed: ?token, ?slug ] }
where { A: bind (?token as ?token) }
then { Session/lookup: [ token: ?token ] }
```
