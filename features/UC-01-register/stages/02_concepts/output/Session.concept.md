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
```

## Operational principle

```
after  User/register:  [ username: "jdoe" ; email: "j@test.com" ; password: "secret" ] => [ userId: u ]
then  Session/grant:   [ userId: u ] => [ sessionId: s ; token: "..." ]
```

## Notes

- `SessionId` is the internal opaque identifier. `token` is the JWT string returned to the client.
- The JWT encodes the `UserId` and `SessionId` so that subsequent requests can be authenticated via `lookup`.
