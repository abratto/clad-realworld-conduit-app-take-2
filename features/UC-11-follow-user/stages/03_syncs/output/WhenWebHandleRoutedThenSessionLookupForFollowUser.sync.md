## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|

sync WhenWebHandleRoutedThenSessionLookupForFollowUser
## Rule
```
when { Web/handle: [ route: ?_ ; body: ?_ ] => [ routed: ?token, ?username ] }
where { A: bind (?token as ?token) }
then { Session/lookup: [ token: ?token ] }
```
