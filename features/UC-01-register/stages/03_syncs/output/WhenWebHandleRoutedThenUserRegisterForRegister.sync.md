sync WhenWebHandleRoutedThenUserRegisterForRegister

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 1 | 3 | `Web/handle: [ route ; body ] => [ routed: ?username, ?email, ?password ]` | `User/register: [ username: ?username ; email: ?email ; password: ?password ]` | none |

## Rule

```
when {
    Web/handle: [ route: ?_ ; body: ?_ ] => [ routed: ?username, ?email, ?password ]
}
where {
    A: bind ( ?username as ?username )
    A: bind ( ?email as ?email )
    A: bind ( ?password as ?password )
}
then {
    User/register: [ username: ?username ; email: ?email ; password: ?password ]
}
```

## Where clause patterns (for Stage 03a audit)

| Binding | Pattern | Source |
|---|---|---|
| `?username` | A | Trigger token (`routed`) |
| `?email` | A | Trigger token (`routed`) |
| `?password` | A | Trigger token (`routed`) |

## Cites

- `../01_usecase/output/usecase.md` — scenario "register-account", main flow steps 2–5

## Notes

- All three fields are carried through the `Web.handle` outcome `Routed(username, email, password)` as declared in the chain table row 1.
