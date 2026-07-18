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
    mints a fresh JWT session token and records userId + now()
    flow token: { action: "Session.grant", userId, sessionId, outcome: "Granted" }

lookup [ sessionId: SessionId ] => [ userId: UserId ]
    session exists — returns the principal it represents
    no state change
    flow token: { action: "Session.lookup", sessionId, outcome: "FOUND" }

lookup [ sessionId: SessionId ] => [ error: "unknown" ]
    no such session exists
    no state change
```

## Operational principle

```
after  Session/grant:  [ userId: u ]       => [ sessionId: s ; token: "..." ]
then  Session/lookup:  [ sessionId: s ]    => [ userId: u ]
then  Session/lookup:  [ sessionId: missing ] => [ error: "unknown" ]
```

## Notes

- `SessionId` must be unguessable (e.g. 128 bits of randomness, base64url).
- `token` is the JWT string returned to the client.
- `Session` does not authenticate anyone — that is `PasswordAuth`'s job — it only records that an authentication has happened.
