sync WhenWebHandleRoutedThenUserLookupByUsernameForViewProfile

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 1 | 2 | `Web/handle: [ route ; body ] => [ routed: ?username, ?token? ]` | `User/lookupByUsername: [ username: ?username ]` | none |

## Rule

```
when { Web/handle: [ route: ?_ ; body: ?_ ] => [ routed: ?username, ?_token ] }
where { A: bind (?username as ?username) }
then { User/lookupByUsername: [ username: ?username ] }
```

## Cites

- ../01_usecase/output/usecase.md — scenario "view-profile", main flow step 2
