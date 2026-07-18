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

lookupByEmail [ email: String ] => [ userId: UserId ; username: String ; email: String ; bio: String ; image: String ]
    precondition {
        email in Dom(State.email)
    }
    postcondition {
        State.email[userId] == email
    }
    no state change
    flow token: { action: "User.lookupByEmail", email, userId, username, outcome: "FOUND" }

lookupByUsername [ username: String ] => [ userId: UserId ]
    precondition {
        username in Dom(State.username)
    }
    postcondition {
        State.username[userId] == username
    }
    no state change
    flow token: { action: "User.lookupByUsername", username, userId, outcome: "FOUND" }
```

## Operational principle

```
after  User/register:         [ username: "alice" ; email: "alice@test.com" ; password: "secret" ] => [ userId: u ]
then  User/lookupByEmail:     [ email: "alice@test.com" ] => [ userId: u ; username: "alice" ]
then  User/lookupByUsername:  [ username: "alice" ] => [ userId: u ]
```

## Notes

- Precondition failures cause **refusal** (`:outcome "refused"`). For `lookupByEmail`, refusal means the email is not registered.
- `lookupByEmail` returns profile fields (username, email, bio, image) so downstream syncs can assemble the response without additional concept-state reads.
