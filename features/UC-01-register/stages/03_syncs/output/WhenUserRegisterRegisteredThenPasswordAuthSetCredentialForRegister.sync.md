sync WhenUserRegisterRegisteredThenPasswordAuthSetCredentialForRegister

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 3 | 4 | `User/register: [ username ; email ; password ] => [ outcome: "Registered" ; userId: ?userId ]` | `PasswordAuth/setCredential: [ userId: ?userId ; password: ?password ]` | none |

## Rule

```
when {
    User/register: [ username: ?username ; email: ?email ; password: ?password ] => [ outcome: "Registered" ; userId: ?userId ]
}
where {
    A: bind ( ?userId as ?userId )
    D: Web: { ?flow :input [ :password ?password ] }
}
then {
    PasswordAuth/setCredential: [ userId: ?userId ; password: ?password ]
}
```

## Where clause patterns (for Stage 03a audit)

| Binding | Pattern | Source |
|---|---|---|
| `?userId` | A | Trigger token (`Registered`) |
| `?password` | D | Concept-state read (Web request input) |

## Cites

- `../01_usecase/output/usecase.md` — scenario "register-account", main flow step 5

## Notes

- The `userId` is emitted by `User.register` as part of its `Registered` outcome flow token.
- The `password` is read from the original Web request input via Pattern D, since it is not carried in the `User.register` outcome.
