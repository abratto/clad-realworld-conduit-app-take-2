sync WhenWebHandleRoutedThenUserLookupByEmailForSignIn

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 1 | 3 | `Web/handle: [ route ; body ] => [ routed: ?email, ?password ]` | `User/lookupByEmail: [ email: ?email ]` | none |

## Rule

```
when {
    Web/handle: [ route: ?_ ; body: ?_ ] => [ routed: ?email, ?password ]
}
where {
    A: bind ( ?email as ?email )
}
then {
    User/lookupByEmail: [ email: ?email ]
}
```

## Where clause patterns

| Binding | Pattern | Source |
|---|---|---|
| `?email` | A | Trigger token (`routed`) |

## Cites

- `../01_usecase/output/usecase.md` — scenario "sign-in", main flow step 3
