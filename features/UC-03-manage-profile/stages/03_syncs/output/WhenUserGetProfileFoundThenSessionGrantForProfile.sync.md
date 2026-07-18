sync WhenUserGetProfileFoundThenSessionGrantForProfile

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 5 | 6 | `User/getProfile: [ userId: ?userId ] => [ outcome: "FOUND" ; username: ?_ ; email: ?_ ; bio: ?_ ; image: ?_ ]` | `Session/grant: [ userId: ?userId ]` | none |

## Rule

```
when { User/getProfile: [ userId: ?userId ] => [ outcome: "FOUND" ] }
where { A: bind (?userId as ?userId) }
then { Session/grant: [ userId: ?userId ] }
```

## Cites

- view-profile main flow step 4
