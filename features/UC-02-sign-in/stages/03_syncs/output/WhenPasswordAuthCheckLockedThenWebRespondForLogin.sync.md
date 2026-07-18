sync WhenPasswordAuthCheckLockedThenWebRespondForLogin

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 4 | 8 | `PasswordAuth/check: [ userId: ?userId ; password: ?_ ] => [ error: "locked" ]` | `Web/respond: [ status: 401 ; body: { errors: { credentials: ["invalid"] } } ]` | `401`, `"invalid"` |

## Rule

```
when {
    PasswordAuth/check: [ userId: ?userId ; password: ?_ ] => [ error: "locked" ]
}
where {
    C: bind ( 401 as ?status )
    C: bind ( { "errors": { "credentials": [ "invalid" ] } } as ?body )
}
then {
    Web/respond: [ status: ?status ; body: ?body ]
}
```

## Where clause patterns

| Binding | Pattern | Source |
|---|---|---|
| `?status` | C | Sync constant `401` |
| `?body` | C | Sync constant error envelope |

## Cites

- `../01_usecase/output/usecase.md` — scenario "sign-in", extension 4b
