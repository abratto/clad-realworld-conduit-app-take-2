sync WhenWebHandleRoutedThenSessionLookupForArticle
## Sync Contract Matrix
| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 1 | 3 | `Web/handle: [ route ; body ] => [ routed: ?token, ?fields ]` | `Session/lookup: [ token: ?token ]` | none |
## Rule
```
when { Web/handle: [ route: ?_ ; body: ?_ ] => [ routed: ?token, ?_ ] }
where { A: bind (?token as ?token) }
then { Session/lookup: [ token: ?token ] }
```
## Cites
- create-article, update-article, delete-article main flow
