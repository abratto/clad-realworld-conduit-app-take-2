sync WhenUserRegisterBlankFieldsThenWebRespondForRegister

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 3 | 2 | `User/register: [ username ; email ; password ] => [ refused: "blankFields" ]` | `Web/respond: [ status: 422 ; body: { errors: { <field>: ["can't be blank"] } } ]` | `422`, `"can't be blank"` |

## Rule

```
when {
    User/register: [ username: ?username ; email: ?email ; password: ?password ] => [ refused: "blankFields" ]
}
where {
    C: bind ( 422 as ?status )
    C: bind ( { "errors": { ?field: [ "can't be blank" ] } } as ?body )
}
then {
    Web/respond: [ status: ?status ; body: ?body ]
}
```

## Where clause patterns (for Stage 03a audit)

| Binding | Pattern | Source |
|---|---|---|
| `?status` | C | Sync constant `422` |
| `?body` | C | Sync constant error envelope |

## Cites

- `../01_usecase/output/usecase.md` — scenario "register-account", extension 2b

## Notes

- Fires when `User.register` refuses specifically with a blank field reason. The specific field name is determined by the `User.register` action's refusal detail.
- The error envelope uses the field name from the refusal, matching the `Web.handle` blank-field error envelope.
