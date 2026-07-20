sync WhenSessionLookupFoundThenFollowGetFollowedUsersForViewFeed
| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 3 | 5 | `Session/lookup: [ token: ?_ ] => [ outcome: "FOUND" ; userId: ?uid ]` | `Follow/getFollowedUsers: [ userId: ?uid ]` | none |
## Rule
```
when { Session/lookup: [ token: ?_ ] => [ outcome: "FOUND" ; userId: ?uid ] }
where { A: bind (?uid as ?userId) }
then { Follow/getFollowedUsers: [ userId: ?userId ] }
```
