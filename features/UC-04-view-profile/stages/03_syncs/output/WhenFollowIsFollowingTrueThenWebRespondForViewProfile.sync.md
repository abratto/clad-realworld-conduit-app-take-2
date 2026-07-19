sync WhenFollowIsFollowingTrueThenWebRespondForViewProfile

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 6a | 7a | `Follow/isFollowing: [ followerId: ?followerId ; profileId: ?profileId ] => [ outcome: "true" ]` | `Web/respond: [ status: 200 ; body: { profile: { username, bio, image, following: true } } ]` | `200`, `true` |

## Rule

```
when { Follow/isFollowing: [ followerId: ?followerId ; profileId: ?profileId ] => [ outcome: "true" ] }
where { C: bind (200 as ?status) ; C: bind (true as ?following) }
then { Web/respond: [ status: ?status ; body: { profile: { username: ?username, bio: ?bio, image: ?image, following: ?following } } ] }
```

## Cites

- ../01_usecase/output/usecase.md — scenario "view-profile", main flow step 4
