sync WhenSessionLookupFoundThenUserLookupByUsernameForFollowUser
## Rule
```
when { Session/lookup: [ token: ?_ ] => [ outcome: "FOUND" ; userId: ?uid ] }
where { D: Web: { ?flow :input [ :username ?username ] } }
then { User/lookupByUsername: [ username: ?username ] }
```
