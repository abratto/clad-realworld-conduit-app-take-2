# UC-09 — View Comments

## Operational principle
A Reader can view all comments on an article, with each comment including the author's profile. Auth is optional.

## Scenario: view-comments
1. Reader sends GET /api/articles/:slug/comments.
2. System looks up article by slug.
3. System retrieves all comments for the article.
4. System responds with HTTP 200 and comments array.
- Extensions: 404 if article not found. 200 with empty array if no comments.
