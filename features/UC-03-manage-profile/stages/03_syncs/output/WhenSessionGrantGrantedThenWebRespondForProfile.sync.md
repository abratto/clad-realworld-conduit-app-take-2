sync WhenSessionGrantGrantedThenWebRespondForProfile

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 6 | 7 | `Session/grant: [ userId: ?userId ] => [ outcome: "Granted" ; token: ?token ]` | `Web/respond: [ status: 200 ; body: { user: { ?email, ?token, ?username, ?bio, ?image } } ]` | `200` |

## Rule

```
when { Session/grant: [ userId: ?userId ] => [ outcome: "Granted" ; token: ?token ] }
where {
    A: bind (?token as ?token)
    D: User/getProfile: { ?userId username: ?username ; email: ?email ; bio: ?bio ; image: ?image }
    C: bind (200 as ?status)
}
then { Web/respond: [ status: ?status ; body: { user: { email: ?email, token: ?token, username: ?username, bio: ?bio, image: ?image } } ] }
```

## Cites

- view-profile and update-profile main flow final step
