concept Session [SessionId, UserId]
purpose
    to validate bearer tokens for authenticated requests

## State

```
userId: SessionId -> UserId  -- mandatory
```

## Actions

```
lookup [ token: String ] => [ userId: UserId ]
    validates token and returns the principal
    no state change
    flow token: { action: "Session.lookup", token, outcome: "FOUND" }
```

## Notes

- Precondition failure causes refusal (invalid/expired token).
