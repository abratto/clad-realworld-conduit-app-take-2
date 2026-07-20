## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|

sync WhenSessionLookupFoundThenUserLookupByUsernameForFollowUser
## Rule
```
when { Session/lookup: [ token: ?_ ] => [ outcome: "FOUND" ; userId: ?uid ] }
where { D: Web: { ?flow :input [ :username ?username ] } }
then { User/lookupByUsername: [ username: ?username ] }
```
