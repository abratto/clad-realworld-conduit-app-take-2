sync WhenUserRegisterDuplicateEmailThenWebRespondForRegister

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 3 | 5 | `User/register: [ username: ?_ ; email: ?email ; password: ?_ ] => [ refused: "duplicateEmail" ]` | `Web/respond: [ status: 409 ; body: { errors: { email: ["has already been taken"] } } ]` | `409`, `"has already been taken"` |

## Rule

```
when {
    User/register: [ username: ?_ ; email: ?email ; password: ?_ ] => [ refused: "duplicateEmail" ]
}
where {
    D: User: { ?userId email: ?email }
    C: bind ( 409 as ?status )
    C: bind ( { "errors": { "email": [ "has already been taken" ] } } as ?body )
}
then {
    Web/respond: [ status: ?status ; body: ?body ]
}
```

## Where clause patterns (for Stage 03a audit)

| Binding | Pattern | Source |
|---|---|---|
| `?status` | C | Sync constant `409` |
| `?body` | D + C | Concept-state read (`User` by email) + constant literal |

## Cites

- `../01_usecase/output/usecase.md` — scenario "register-account", extension 4a

## Notes

- Fires when `User.register` refuses and the attempted email already exists in User state.
- The chain table row 5 (single `refused` → 409) is materialized as two syncs here because the refusal can be caused by either a duplicate username or duplicate email, and each needs a distinct response body.
