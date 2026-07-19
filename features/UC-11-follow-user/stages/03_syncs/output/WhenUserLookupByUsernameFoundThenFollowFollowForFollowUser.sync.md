sync WhenUserLookupByUsernameFoundThenFollowFollowForFollowUser
## Rule
```
when { User/lookupByUsername: [ username: ?username ] => [ outcome: "FOUND" ; userId: ?profileId ] }
where { A: bind (?profileId as ?profileId) ; B: bind (?uid as ?followerId) }
then { Follow/follow: [ followerId: ?followerId ; profileId: ?profileId ] }
```
