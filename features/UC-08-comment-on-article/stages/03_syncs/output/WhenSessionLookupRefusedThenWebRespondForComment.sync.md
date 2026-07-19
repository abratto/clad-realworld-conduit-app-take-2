sync WhenSessionLookupRefusedThenWebRespondForComment

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 3 | 4 | `Session/lookup: [ token: ?_ ] => [ refused ]` | `Web/respond: [ status: 401 ; body: { errors: { token: ["is invalid"] } } ]` | `401`, `"is invalid"` |

## Rule

```
when { Session/lookup: [ token: ?_ ] => [ refused ] }
where { C: bind (401 as ?status) ; C: bind ({"errors":{"token":["is invalid"]}} as ?body) }
then { Web/respond: [ status: ?status ; body: ?body ] }
```

## Cites

- `../01_usecase/output/usecase.md` — scenarios "add-comment", "delete-comment", extension "invalid token"
