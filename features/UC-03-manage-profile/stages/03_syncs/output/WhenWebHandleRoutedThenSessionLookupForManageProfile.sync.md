sync WhenWebHandleRoutedThenSessionLookupForManageProfile

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 1 | 3 | `Web/handle: [ route: ?_ ; body: ?_ ] => [ routed: ?token ]` | `Session/lookup: [ token: ?token ]` | none |

## Rule

```
when { Web/handle: [ route: ?_ ; body: ?_ ] => [ routed: ?token ] }
where { A: bind (?token as ?token) }
then { Session/lookup: [ token: ?token ] }
```

## Cites

- `../01_usecase/output/usecase.md` — scenario "view-profile" and "update-profile", main flow step 2
