## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|

sync WhenFollowFollowFollowedThenWebRespondForFollowUser
## Rule
```
when { Follow/follow: [ followerId: ?_ ; profileId: ?_ ] => [ outcome: "Followed" ] }
where { C: bind (200 as ?status) }
then { Web/respond: [ status: ?status ; body: { profile: { following: true } } ] }
```
