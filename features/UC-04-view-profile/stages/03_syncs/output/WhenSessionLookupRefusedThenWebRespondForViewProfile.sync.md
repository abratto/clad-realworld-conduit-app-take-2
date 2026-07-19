sync WhenSessionLookupRefusedThenWebRespondForViewProfile

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 5a | 5b | `Session/lookup: [ token: ?_ ] => [ refused ]` | `Web/respond: [ status: 200 ; body: { profile: { username, bio, image, following: false } } ]` | `200`, `false` |

## Rule

```
when { Session/lookup: [ token: ?_ ] => [ refused ] }
where { C: bind (200 as ?status) ; C: bind (false as ?following) ; B: bind (?profileId as ?profileId) }
then { Web/respond: [ status: ?status ; body: { profile: { username: ?username, bio: ?bio, image: ?image, following: ?following } } ] }
```

## Cites

- ../01_usecase/output/usecase.md — scenario "view-profile", extension 3c (invalid token)
