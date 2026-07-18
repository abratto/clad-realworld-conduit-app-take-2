sync WhenSessionLookupFoundThenUserGetProfileForProfile

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 3 | 5 | `Session/lookup: [ token: ?token ] => [ outcome: "FOUND" ; userId: ?userId ]` | `User/getProfile: [ userId: ?userId ]` | none |

## Rule

```
when { Session/lookup: [ token: ?_ ] => [ outcome: "FOUND" ; userId: ?userId ] }
where { A: bind (?userId as ?userId) }
then { User/getProfile: [ userId: ?userId ] }
```

## Cites

- view-profile main flow step 3
