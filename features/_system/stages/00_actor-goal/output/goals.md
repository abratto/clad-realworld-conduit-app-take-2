# Goals

| Actor | Goal | Rationale | Priority | In scope? |
|---|---|---|---|---|
| Member | Register Account | to create a new account with email, username, and password so that the user can authenticate and perform Member actions | P0 | yes |
| Member | Sign In | to authenticate with email and password so that the Member obtains a JWT for subsequent requests | P0 | yes |
| Member | Manage Profile | to view and update own profile fields (email, username, bio, image, password) so that the Member can keep their identity current | P1 | yes |
| Reader | Browse Articles | to discover articles filtered by tag, author, or favorited status, with pagination sorted by most recent | P0 | yes |
| Reader | Read Article | to view a single article's full body, metadata (slug, title, description, tagList), author profile, and favorite count by slug | P0 | yes |
| Member | Manage Articles | to create, update, and delete own articles so that the Member can publish and maintain their content; creating must generate a unique slug | P0 | yes |
| Reader | View Comments | to see comments attached to an article with author details | P0 | yes |
| Member | Comment on Article | to add a comment body to an article and delete own comments | P0 | yes |
| Member | Favorite Article | to favorite and unfavorite an article, updating its favoritesCount and the Member's favorited flag | P0 | yes |
| Reader | View Profile | to see a user's public profile (username, bio, image) with a dynamic "following" indicator when the viewer is authenticated | P0 | yes |
| Member | Follow User | to follow and unfollow other Members so that articles by followed users appear in the feed | P0 | yes |
| Member | View Feed | to see a paginated feed of articles published by users the Member follows, sorted by most recent | P0 | yes |
| Reader | List Tags | to retrieve all unique string tags extracted across all articles | P1 | yes |

## Adapter contract

This system interfaces with the RealWorld Conduit API spec. The adapter format is not a design choice — it is fixed by the external spec.

- **Source:** [RealWorld Backend API Spec](https://docs.realworld.show/specifications/backend/introduction/)
- **OpenAPI spec:** [openapi.yml](https://github.com/realworld-apps/realworld/blob/main/specs/api/openapi.yml)
- **Contract test suite:** [Hurl collection](https://github.com/realworld-apps/realworld/tree/main/specs/api/hurl) + [`run-api-tests-hurl.sh`](https://github.com/realworld-apps/realworld/blob/main/specs/api/run-api-tests-hurl.sh)
- **Fixed conventions:** See `output/port-spec.md`

## Out of scope

| Actor | Goal | Rationale |
|---|---|---|
| Member | Manage Roles (admin/moderator) | not required by the Conduit spec |
| Member | Logout | not part of the Conduit API — tokens are stateless JWT |
| System | Notify (push/email) | the Conduit spec has no notification surface |
| System | Moderate Content | no admin or moderation panel in the spec |
| Reader | Full-text Search | the spec only supports filtered listing, not free-text search |
| Member | Password Reset | not part of the Conduit spec |
