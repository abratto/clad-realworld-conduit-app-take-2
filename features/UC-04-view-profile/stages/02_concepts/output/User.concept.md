concept User [UserId]
purpose
    to own registered Member identity and profile data

## State

```
username: UserId -> String   -- mandatory, unique
email:    UserId -> String   -- mandatory, unique
bio:      UserId -> String   -- optional, defaults to null
image:    UserId -> String   -- optional, defaults to null
```

## Actions

```
lookupByUsername [ username: String ] => [ userId: UserId ; username: String ; bio: String ; image: String ]
    precondition { username in Dom(State.username) }
    postcondition { State.username[userId] == username }
    no state change
    flow token: { action: "User.lookupByUsername", username, userId, outcome: "FOUND" }
```

## Notes

- Precondition failure causes **refusal** (`:outcome "refused"`). For lookupByUsername, refusal means the username is not registered.
- The `lookupByUsername` action returns profile fields (username, bio, image) so downstream syncs can assemble the response. Email is excluded from public profile data.
