# Responsibility map — View Feed

| Concept | Owned state | Owned actions | Notes |
|---|---|---|---|
| `Web` | route table | `handle`, `respond` | Bootstrap |
| `Session` | token → userId | `lookup` | Auth |
| `Follow` | follower → followee | `getFollowedUsers` | New action: returns list of followed userIds |
| `Article` | article data | `listByAuthors` | New action: filter articles by author list |
