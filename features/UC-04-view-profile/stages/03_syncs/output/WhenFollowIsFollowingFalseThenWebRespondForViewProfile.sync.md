sync WhenFollowIsFollowingFalseThenWebRespondForViewProfile

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 6a | 7b | `Follow/isFollowing: [ followerId: ?followerId ; profileId: ?profileId ] => [ outcome: "false" ]` | `Web/respond: [ status: 200 ; body: { profile: { username, bio, image, following: false } } ]` | `200`, `false` |

## Rule

```
when { Follow/isFollowing: [ followerId: ?followerId ; profileId: ?profileId ] => [ outcome: "false" ] }
where { C: bind (200 as ?status) ; C: bind (false as ?following) }
then { Web/respond: [ status: ?status ; body: { profile: { username: ?username, bio: ?bio, image: ?image, following: ?following } } ] }
```

## Cites

- ../01_usecase/output/usecase.md — scenario "view-profile", extension 3c (not following)
