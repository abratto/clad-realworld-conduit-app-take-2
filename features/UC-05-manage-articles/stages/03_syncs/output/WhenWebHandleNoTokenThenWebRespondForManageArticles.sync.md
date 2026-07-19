sync WhenWebHandleNoTokenThenWebRespondForManageArticles

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 1 | 2 | `Web/handle: [ route ; body ] => [ refused: "noToken" ]` | `Web/respond: [ status: 401 ; body: { errors: { token: ["is missing"] } } ]` | `401`, `"is missing"` |

## Rule

```
when { Web/handle: [ route: ?_ ; body: ?_ ] => [ refused: "noToken" ] }
where { C: bind (401 as ?status) ; C: bind ({"errors":{"token":["is missing"]}} as ?body) }
then { Web/respond: [ status: ?status ; body: ?body ] }
```

## Cites

- `../01_usecase/output/usecase.md` — scenarios "create-article", "update-article", "delete-article", extension "token missing"
