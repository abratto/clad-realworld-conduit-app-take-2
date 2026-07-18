concept User [UserId]
purpose
    to own registered Member identity — username, email, password hash, bio, and image.

## State

```
username:     UserId -> String   -- mandatory, unique across all users
email:        UserId -> String   -- mandatory, unique across all users
passwordHash: UserId -> String   -- mandatory
bio:          UserId -> String   -- optional, defaults to null
image:        UserId -> String   -- optional, defaults to null
```

## Actions

```
register [ username: String ; email: String ; password: String ] => [ userId: UserId ]
    precondition {
        username not in Cod(State.username)
        email not in Cod(State.email)
    }
    postcondition {
        State.username'[userId] = username
        State.email'[userId] = email
        State.passwordHash'[userId] = hash(password)
        State.bio'[userId] = null
        State.image'[userId] = null
        userId is freshly minted
    }
    flow token: { action: "User.register", username, email, userId, outcome: "Registered" }
```

## Operational principle

```
after  User/register:  [ username: "jdoe" ; email: "j@test.com" ; password: "secret" ] => [ userId: u ]
```

## Notes

- Precondition failure (username or email already taken) causes **refusal** (`:outcome "refused"`) — no state changes.
  The sync layer inspects the User state to determine which field caused the conflict.
- Password hashing is an internal implementation detail. No external concept or sync reads the password hash.
- `bio` and `image` are always `null` for newly registered users. They are updated via UC-03-manage-profile.
