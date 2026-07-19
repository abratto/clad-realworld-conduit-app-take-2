sync WhenWebHandleRoutedThenSessionLookupForCommentOnArticle

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 1 | 3 | `Web/handle: [ route ; body ] => [ routed: ?token, ?_ ]` | `Session/lookup: [ token: ?token ]` | none |

## Rule

```
when { Web/handle: [ route: ?_ ; body: ?_ ] => [ routed: ?token, ?_ ] }
where { A: bind (?token as ?token) }
then { Session/lookup: [ token: ?token ] }
```

## Cites

- `../01_usecase/output/usecase.md` — scenarios "add-comment", "delete-comment", main flow step 2
