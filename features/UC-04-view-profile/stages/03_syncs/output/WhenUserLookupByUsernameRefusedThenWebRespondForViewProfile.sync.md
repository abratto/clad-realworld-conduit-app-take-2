sync WhenUserLookupByUsernameRefusedThenWebRespondForViewProfile

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 2 | 3 | `User/lookupByUsername: [ username: ?username ] => [ refused ]` | `Web/respond: [ status: 404 ; body: { errors: { profile: ["not found"] } } ]` | `404`, `"not found"` |

## Rule

```
when { User/lookupByUsername: [ username: ?username ] => [ refused ] }
where { C: bind (404 as ?status) ; C: bind ({"errors":{"profile":["not found"]}} as ?body) }
then { Web/respond: [ status: ?status ; body: ?body ] }
```

## Cites

- ../01_usecase/output/usecase.md — scenario "view-profile", extension 3a
