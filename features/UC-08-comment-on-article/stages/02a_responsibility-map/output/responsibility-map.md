# Responsibility map — Comment on Article

| Concept | Owned state | Owned actions | Notes |
|---|---|---|---|
| `Web` | route table | `handle`, `respond` | Bootstrap |
| `Session` | token → userId | `lookup` | Auth |
| `Article` | article state | `getBySlug` | Verify article exists |
| `Comment` | id, body, authorId, articleId, timestamps | `add`, `delete`, `authorCheck` | New concept |
