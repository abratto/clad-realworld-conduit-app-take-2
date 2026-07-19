# UC-11 — Follow User

## Operational principle
A signed-in Member can follow or unfollow another Member. Following adds them to the Member's feed. Unfollowing removes them.

## Scenarios
### Scenario: follow-user
1. Member sends POST /api/profiles/:username/follow with JWT.
2. System validates token, looks up target user, records follow.
3. System responds with HTTP 200 and profile (following: true).

### Scenario: unfollow-user
1. Member sends DELETE /api/profiles/:username/follow with JWT.
2. System validates token, looks up target user, removes follow.
3. System responds with HTTP 200 and profile (following: false).
