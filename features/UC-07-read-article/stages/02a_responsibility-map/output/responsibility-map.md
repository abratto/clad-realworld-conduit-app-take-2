# Responsibility map — Read Article

| Concept | Owned state | Owned actions | Notes |
|---|---|---|---|
| `Web` | route table | `handle`, `respond` | Bootstrap |
| `Article` | slug, title, desc, body, authorId, tagList, timestamps | `getBySlug` | Already exists from UC-05 |
| `User` | username, bio, image | `getProfile` | Resolve author |
