sync WhenUserRegisterRegisteredThenSessionGrantForRegister

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 3 | 4 | `User/register: [ username ; email ; password ] => [ outcome: "Registered" ; userId: ?userId ]` | `Session/grant: [ userId: ?userId ]` | none |

## Rule

```
when {
    User/register: [ username: ?_ ; email: ?_ ; password: ?_ ] => [ outcome: "Registered" ; userId: ?userId ]
}
where {
    A: bind ( ?userId as ?userId )
}
then {
    Session/grant: [ userId: ?userId ]
}
```

## Where clause patterns (for Stage 03a audit)

| Binding | Pattern | Source |
|---|---|---|
| `?userId` | A | Trigger token (`Registered`) |

## Cites

- `../01_usecase/output/usecase.md` — scenario "register-account", main flow step 6

## Notes

- The `userId` is emitted by `User.register` as part of its `Registered` outcome flow token.
