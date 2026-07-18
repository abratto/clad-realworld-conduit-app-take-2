sync WhenSessionGrantGrantedThenWebRespondForRegister

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 4 | 6 | `Session/grant: [ userId: ?userId ] => [ outcome: "Granted" ; token: ?token ]` | `Web/respond: [ status: 201 ; body: { user: { ?username, ?email, bio: null, image: null, token: ?token } } ]` | `201`, `null` |

## Rule

```
when {
    Session/grant: [ userId: ?userId ] => [ outcome: "Granted" ; token: ?token ]
}
where {
    D: User: { ?userId username: ?username ; email: ?email }
    A: bind ( ?token as ?token )
    C: bind ( 201 as ?status )
    C: bind ( null as ?null )
}
then {
    Web/respond: [ status: ?status ; body: { user: { username: ?username, email: ?email, bio: ?null, image: ?null, token: ?token } } ]
}
```

## Where clause patterns (for Stage 03a audit)

| Binding | Pattern | Source |
|---|---|---|
| `?username` | D | Concept-state read (`User` state) |
| `?email` | D | Concept-state read (`User` state) |
| `?token` | A | Trigger token (`Granted`) |
| `?status` | C | Sync constant `201` |
| `?null` | C | Sync constant `null` |

## Cites

- `../01_usecase/output/usecase.md` — scenario "register-account", main flow step 7

## Notes

- The response body includes `bio: null` and `image: null` per the Conduit spec for a newly registered user. These fields are read from User state (they default to `null` on registration).
- The `?token` is the JWT emitted by `Session.grant`.
