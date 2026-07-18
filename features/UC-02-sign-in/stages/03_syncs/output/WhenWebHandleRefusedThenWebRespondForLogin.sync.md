sync WhenWebHandleRefusedThenWebRespondForLogin

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 1 | 2 | `Web/handle: [ route ; body ] => [ refused: "blankFields" ]` | `Web/respond: [ status: 422 ; body: { errors: { <field>: ["can't be blank"] } } ]` | `422`, `"can't be blank"` |

## Rule

```
when {
    Web/handle: [ route: ?_ ; body: ?_ ] => [ refused: "blankFields" ]
}
where {
    C: bind ( 422 as ?status )
    C: bind ( { "errors": { ?field: [ "can't be blank" ] } } as ?body )
}
then {
    Web/respond: [ status: ?status ; body: ?body ]
}
```

## Where clause patterns

| Binding | Pattern | Source |
|---|---|---|
| `?status` | C | Sync constant `422` |
| `?body` | C | Sync constant error envelope |

## Cites

- `../01_usecase/output/usecase.md` — scenario "sign-in", extension 2a
