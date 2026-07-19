sync WhenSessionLookupFoundThenArticleGetBySlugForComment

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 3 | 5 | `Session/lookup: [ token: ?_ ] => [ outcome: "FOUND" ; userId: ?memberId ]` | `Article/getBySlug: [ slug: ?slug ]` | none |

## Rule

```
when { Session/lookup: [ token: ?_ ] => [ outcome: "FOUND" ; userId: ?memberId ] }
where {
    A: bind (?memberId as ?memberId)
    D: Web: { ?flow :input [ :slug ?slug ] }
}
then { Article/getBySlug: [ slug: ?slug ] }
```

## Cites

- `../01_usecase/output/usecase.md` — scenarios "add-comment", "delete-comment", main flow step 3
