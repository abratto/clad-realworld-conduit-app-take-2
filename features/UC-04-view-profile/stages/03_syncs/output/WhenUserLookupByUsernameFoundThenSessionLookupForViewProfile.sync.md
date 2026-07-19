sync WhenUserLookupByUsernameFoundThenSessionLookupForViewProfile

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 2 | 5a | `User/lookupByUsername: [ username: ?username ] => [ outcome: "FOUND" ; userId: ?userId ; username: ?_ ; bio: ?_ ; image: ?_ ]` | `Session/lookup: [ token: ?token ]` | none |

## Rule

```
when { User/lookupByUsername: [ username: ?username ] => [ outcome: "FOUND" ; userId: ?userId ; username: ?_ ; bio: ?_ ; image: ?_ ] }
where {
    A: bind (?userId as ?profileId)
    D: Web: { ?flow :input [ :token ?token ] }
    FILTER (BOUND(?token) && STR(?token) != "")
}
then { Session/lookup: [ token: ?token ] }
```

## Cites

- ../01_usecase/output/usecase.md — scenario "view-profile", main flow step 3 (authenticated path)
