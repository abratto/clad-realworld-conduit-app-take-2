sync WhenPasswordAuthCheckOkThenSessionGrantForLogin

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 4 | 6 | `PasswordAuth/check: [ userId: ?userId ; password: ?_ ] => [ outcome: "OK" ]` | `Session/grant: [ userId: ?userId ]` | none |

## Rule

```
when {
    PasswordAuth/check: [ userId: ?userId ; password: ?_ ] => [ outcome: "OK" ]
}
where {
    A: bind ( ?userId as ?userId )
}
then {
    Session/grant: [ userId: ?userId ]
}
```

## Where clause patterns

| Binding | Pattern | Source |
|---|---|---|
| `?userId` | A | Trigger token (`OK`) |

## Cites

- `../01_usecase/output/usecase.md` — scenario "sign-in", main flow step 5
