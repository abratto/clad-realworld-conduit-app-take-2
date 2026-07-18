concept PasswordAuth [UserId]
purpose
    to manage password credentials

## State

```
passwordHash:   UserId -> PasswordHash     -- mandatory
failedAttempts: UserId -> Int              -- default 0
lockedUntil:    UserId -> Timestamp        -- optional
```

## Actions

```
setCredential [ userId: UserId ; password: String ] => [ ok ]
    stores hash(password) and resets failedAttempts
    flow token: { action: "PasswordAuth.setCredential", userId, outcome: "ok" }

check [ userId: UserId ; password: String ] => [ OK | error:badPassword | error:locked ]
    verifies password
```

## Notes

- `setCredential` is called during profile update when password is changed.
