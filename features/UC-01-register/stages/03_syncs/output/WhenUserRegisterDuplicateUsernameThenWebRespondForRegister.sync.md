sync WhenUserRegisterDuplicateUsernameThenWebRespondForRegister

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 3 | 5 | `User/register: [ username: ?username ; email: ?_ ; password: ?_ ] => [ refused: "duplicateUsername" ]` | `Web/respond: [ status: 409 ; body: { errors: { username: ["has already been taken"] } } ]` | `409`, `"has already been taken"` |

## Rule

```
when {
    User/register: [ username: ?username ; email: ?_ ; password: ?_ ] => [ refused: "duplicateUsername" ]
}
where {
    D: User: { ?userId username: ?username }
    C: bind ( 409 as ?status )
    C: bind ( { "errors": { "username": [ "has already been taken" ] } } as ?body )
}
then {
    Web/respond: [ status: ?status ; body: ?body ]
}
```

## Where clause patterns (for Stage 03a audit)

| Binding | Pattern | Source |
|---|---|---|
| `?status` | C | Sync constant `409` |
| `?body` | D + C | Concept-state read (`User` by username) + constant literal |

## Cites

- `../01_usecase/output/usecase.md` — scenario "register-account", extension 3a

## Notes

- Fires when `User.register` refuses and the attempted username already exists in User state.
- A sibling sync `WhenUserRegisterDuplicateEmailThenWebRespondForRegister` handles the email case on the same trigger.
