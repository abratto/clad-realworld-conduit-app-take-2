concept Follow [UserId]
purpose
    to track follow relationships between Members

## State

```
following: (UserId, UserId) -> Boolean   -- mandatory
```

## Actions

```
isFollowing [ followerId: UserId ; profileId: UserId ] => [ true ]
    followerId follows profileId
    no state change
    flow token: { action: "Follow.isFollowing", followerId, profileId, outcome: "true" }

isFollowing [ followerId: UserId ; profileId: UserId ] => [ false ]
    followerId does not follow profileId
    no state change
    flow token: { action: "Follow.isFollowing", followerId, profileId, outcome: "false" }
```

## Notes

- `isFollowing` is a read-only query. Full Follow concept (follow/unfollow actions) is implemented in UC-11-follow-user.
- The `following` state uses a pair of UserIds as the key.
