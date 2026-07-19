sync WhenFollowGetFollowedUsersListedThenArticleListByAuthorsForViewFeed
## Sync Contract Matrix
| 5 | 6 | `Follow/getFollowedUsers: [ userId: ?uid ] => [ outcome: "Listed" ; followeeIds: ?ids ]` | `Article/listByAuthors: [ authorIds: ?ids ; limit: ?limit ; offset: ?offset ]` | none |
## Rule
```
when { Follow/getFollowedUsers: [ userId: ?_ ] => [ outcome: "Listed" ; followeeIds: ?ids ] }
where { A: bind (?ids as ?authorIds) ; D: Web: { ?flow :input [ :limit ?limit ; :offset ?offset ] } }
then { Article/listByAuthors: [ authorIds: ?authorIds ; limit: ?limit ; offset: ?offset ] }
```
