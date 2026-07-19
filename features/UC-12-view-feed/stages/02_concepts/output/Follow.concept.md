concept Follow [UserId]
purpose to manage follow relationships
## Actions
```
getFollowedUsers [ userId: UserId ] => [ Listed(followeeIds) ]
    returns list of users that userId follows
    no state change
    flow token: { action: "Follow.getFollowedUsers", userId, outcome: "Listed" }
```
