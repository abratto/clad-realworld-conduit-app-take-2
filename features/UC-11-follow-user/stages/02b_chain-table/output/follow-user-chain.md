# Chain — follow/unfollow
## follow
| # | When | Then | Outcome |
|---|---|---|---|
| 1 | `Web/request[POST /api/profiles/:username/follow]` | `Web.handle` | `Routed(token, username)` |
| 2 | `Web.handle[Routed]` | `Session.lookup` | `FOUND(followerId)` |
| 3 | `Session.lookup[FOUND]` | `User.lookupByUsername` | `FOUND(profileUserId)` |
| 4 | `User.lookupByUsername[FOUND]` | `Follow.follow` | `Followed` |
| 5 | `Follow.follow[Followed]` | `Web.respond[200]` | `Sent` |

## unfollow
| 1-3 | Same | |
| 4 | `User.lookupByUsername[FOUND]` | `Follow.unfollow` | `Unfollowed` |
| 5 | `Follow.unfollow[Unfollowed]` | `Web.respond[200]` | `Sent` |
