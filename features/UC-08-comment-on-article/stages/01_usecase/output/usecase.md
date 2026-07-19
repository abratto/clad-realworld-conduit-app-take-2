# UC-08 — Comment on Article

## Completeness level

- [x] **Fully Dressed**

## Operational principle

A signed-in Member can add a comment to any article and delete their own comments. Comments include a body, timestamps, and the author's profile.

## Actors

- **Member** — authenticated user

## Scenarios

### Scenario: add-comment
- **Trigger:** Member submits a comment body on an article.
- **Main flow:**
  1. Member sends POST /api/articles/:slug/comments with comment body + JWT.
  2. System validates token, looks up article, creates comment.
  3. System responds with HTTP 200 and the comment object with author profile.
- **Extensions:** 401 (no auth), 404 (article not found), 422 (blank body).

### Scenario: delete-comment
- **Trigger:** Member deletes their own comment.
- **Main flow:**
  1. Member sends DELETE /api/articles/:slug/comments/:id with JWT.
  2. System validates token, looks up comment, verifies ownership.
  3. System deletes the comment.
  4. System responds with HTTP 200 (or 204).
- **Extensions:** 401, 403 (not author), 404 (comment not found).
