sync WhenUserLookupByEmailFoundThenPasswordAuthCheckForLogin

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 3 | 4 | `User/lookupByEmail: [ email: ?email ] => [ outcome: "FOUND" ; userId: ?userId ; username: ?_ ; email: ?_ ; bio: ?_ ; image: ?_ ]` | `PasswordAuth/check: [ userId: ?userId ; password: ?password ]` | none |

## Rule

```
when {
    User/lookupByEmail: [ email: ?email ] => [ outcome: "FOUND" ; userId: ?userId ; username: ?_ ; email: ?_ ; bio: ?_ ; image: ?_ ]
}
where {
    A: bind ( ?userId as ?userId )
    D: Web/request: { ?flow :input [ :password ?password ] }
}
then {
    PasswordAuth/check: [ userId: ?userId ; password: ?password ]
}
```

## Where clause patterns

| Binding | Pattern | Source |
|---|---|---|
| `?userId` | A | Trigger token (`FOUND`) |
| `?password` | D | Concept-state read (Web request input) |

## Cites

- `../01_usecase/output/usecase.md` — scenario "sign-in", main flow step 4
