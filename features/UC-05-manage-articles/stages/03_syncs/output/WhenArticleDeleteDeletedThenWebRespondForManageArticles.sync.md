sync WhenArticleDeleteDeletedThenWebRespondForManageArticles

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 7 | 8 | `Article/delete: [ slug: ?_ ] => [ outcome: "Deleted" ]` | `Web/respond: [ status: 204 ]` | `204` |

## Rule

```
when { Article/delete: [ slug: ?_ ] => [ outcome: "Deleted" ] }
where { C: bind (204 as ?status) }
then { Web/respond: [ status: ?status ] }
```

## Cites

- `../01_usecase/output/usecase.md` — scenario "delete-article", main flow step 4
