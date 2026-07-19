sync WhenSessionGrantGrantedThenWebRespondForLogin

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 6 | 9 | `Session/grant: [ userId: ?userId ] => [ outcome: "Granted" ; token: ?token ]` | `Web/respond: [ status: 200 ; body: { user: { email: ?email, token: ?token, username: ?username, bio: ?bio, image: ?image } } ]` | `200`, `null` |

## Rule

```
when {
    Session/grant: [ userId: ?userId ] => [ outcome: "Granted" ; token: ?token ]
}
where {
    A: bind ( ?token as ?token )
    A: bind ( ?userId as ?userId )
    D: User: { ?userId username: ?username ; email: ?email ; bio: ?bio ; image: ?image }
    C: bind ( 200 as ?status )
}
then {
    Web/respond: [ status: ?status ; body: { user: { email: ?email, token: ?token, username: ?username, bio: ?bio, image: ?image } } ]
}
```

## Where clause patterns

| Binding | Pattern | Source |
|---|---|---|
| `?token` | A | Trigger token (`Granted`) |
| `?userId` | A | Trigger token (`Granted`) |
| `?username` | D | Concept-state read (User state) |
| `?email` | D | Concept-state read (User state) |
| `?bio` | D | Concept-state read (User state) |
| `?image` | D | Concept-state read (User state) |
| `?status` | C | Sync constant `200` |

## Cites

- `../01_usecase/output/usecase.md` — scenario "sign-in", main flow step 6
