concept User [UserId]
purpose
    to own registered Member identity — username, email, password hash, bio, and image.

## State

```
username:     UserId -> String   -- mandatory, unique
email:        UserId -> String   -- mandatory, unique
passwordHash: UserId -> String   -- mandatory
bio:          UserId -> String   -- optional, defaults to null
image:        UserId -> String   -- optional, defaults to null
```

## Actions

```
register [ username: String ; email: String ; password: String ] => [ userId: UserId ]
    precondition { username not taken, email not taken }
    postcondition { creates user with all fields }
    flow token: { action: "User.register", username, email, userId, outcome: "Registered" }

lookupByEmail [ email: String ] => [ FOUND(userId, username, email, bio, image) ]
    precondition { email exists }
    no state change
    flow token: { action: "User.lookupByEmail", email, userId, outcome: "FOUND" }

lookupByUsername [ username: String ] => [ userId: UserId ]
    precondition { username exists }
    no state change
    flow token: { action: "User.lookupByUsername", username, userId, outcome: "FOUND" }

getProfile [ userId: UserId ] => [ FOUND(username, email, bio, image) ]
    precondition { userId exists }
    no state change
    flow token: { action: "User.getProfile", userId, outcome: "FOUND" }

updateProfile [ userId: UserId ; username?: String ; email?: String ; bio?: String ; image?: String ] => [ Updated ]
    precondition {
        if username provided: username not taken (or same as current)
        if email provided: email not taken (or same as current)
        username and email not blank when provided
    }
    postcondition {
        State.username'[userId] = username if provided
        State.email'[userId] = email if provided
        State.bio'[userId] = bio if provided
        State.image'[userId] = image if provided
    }
    flow token: { action: "User.updateProfile", userId, outcome: "Updated" }
```

## Notes

- Precondition failures on `updateProfile` cause refusal with specific field name in the reason (`:refusalReason`).
- `getProfile` is a convenience action that returns profile fields for a given userId without needing a lookup by email/username.
