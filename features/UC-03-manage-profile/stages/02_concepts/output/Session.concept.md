concept Session [SessionId, UserId]
purpose
    to manage bearer-token sessions for a Member

## State

```
userId:   SessionId -> UserId      -- mandatory
openedAt: SessionId -> Timestamp   -- mandatory
```

## Actions

```
grant [ userId: UserId ] => [ sessionId: SessionId ; token: String ]
    mints a fresh JWT session token
    flow token: { action: "Session.grant", userId, sessionId, outcome: "Granted" }

lookup [ token: String ] => [ userId: UserId ]
    validates token and returns the principal
    no state change
    flow token: { action: "Session.lookup", token, outcome: "FOUND" }

lookup [ token: String ] => [ error: "unknown" ]
    no such session exists
    no state change
```

## Notes

- Token rotation: each profile access/update should call `grant` to mint a new token.
