concept Follow [UserId]
purpose to manage follow relationships
## Actions
```
follow [ followerId: UserId ; profileId: UserId ] => [ Followed ]
    postcondition { follow recorded }
    flow token: { action: "Follow.follow", followerId, profileId, outcome: "Followed" }

unfollow [ followerId: UserId ; profileId: UserId ] => [ Unfollowed ]
    postcondition { follow removed }
    flow token: { action: "Follow.unfollow", followerId, profileId, outcome: "Unfollowed" }
```
