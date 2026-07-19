sync WhenArticleAuthorCheckIsAuthorThenArticleDeleteForArticle

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 7 | 7 | `Article/authorCheck: [ articleId: ?_ ; memberId: ?_ ] => [ outcome: "IsAuthor" ]` | `Article/delete: [ slug: ?slug ]` | none |

## Rule

```
when { Article/authorCheck: [ articleId: ?_ ; memberId: ?_ ] => [ outcome: "IsAuthor" ] }
where { B: bind (?slug as ?slug) }
then { Article/delete: [ slug: ?slug ] }
```

## Cites

- `../01_usecase/output/usecase.md` — scenario "delete-article", main flow step 3
