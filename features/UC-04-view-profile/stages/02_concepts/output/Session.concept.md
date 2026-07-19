concept Session [SessionId, UserId]
purpose
    to validate bearer tokens for authenticated requests

## State

```
userId: SessionId -> UserId      -- mandatory
```

## Actions

```
lookup [ token: String ] => [ userId: UserId ]
    validates token and returns the principal
    no state change
    flow token: { action: "Session.lookup", token, outcome: "FOUND" }

lookup [ token: String ] => [ error: "unknown" ]
    no such session exists
    no state change
```

## Notes

- `Session.lookup` is optional in this UC — used only when the viewer provides a token.
