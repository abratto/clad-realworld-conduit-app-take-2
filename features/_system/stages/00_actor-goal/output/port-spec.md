# Port Specification — Conduit (RealWorld) Backend

## Source

[RealWorld Backend API Spec](https://docs.realworld.show/specifications/backend/introduction/)
[OpenAPI spec](https://github.com/realworld-apps/realworld/blob/main/specs/api/openapi.yml)

## Adapter type

HTTP REST (JSON)

## Fixed conventions

Conventions imposed by the RealWorld Conduit API spec that are NOT derivable from use cases alone:

1. **Error envelope:** `{"errors": {"<field>": ["<message>"]}}` — always wrapped in `errors` object, never a top-level string.
2. **Resource wrapping:** All single-resource responses are wrapped: `{"user": {...}}`, `{"article": {...}}`, `{"comment": {...}}`. Collection responses are wrapped: `{"articles": [...]}`, `{"comments": [...]}`, and include metadata objects: `{"articlesCount": N}`, `{"tagsCount": N}`.
3. **Nested author object:** Every article and comment response includes a nested `author` object with `username`, `bio`, `image`, and `following`.
4. **Profile response:** `{"profile": {"username": "...", "bio": "...", "image": "...", "following": true|false}}` — never wrapped in `user`.
5. **Article slugs:** Generated from title (kebab-case + unique suffix), not UUIDs. Slug is the lookup key.
6. **JWT auth:** `Authorization: Token <jwt>` header, not `Bearer`. Token must be included as `Token` prefix, not `Bearer`.
7. **Pagination:** `limit` (default 20) and `offset` (default 0) query params. Results include `articlesCount` not `total`.
8. **Feed vs list:** `/api/articles/feed` returns articles from followed users; `/api/articles` returns global feed. Both use `limit`/`offset`.
9. **Favorite count:** `favoritesCount` is an integer field on every article in every response, always present.
10. **Tags:** Flat string list in `tagList` on articles. No tag CRUD — tags are extracted from articles.
11. **Auth optional for reads:** `GET` endpoints (articles, article, comments, tags, profiles) work without auth but return `following`/`favorited` as `false` when unauthenticated.
12. **Comment ownership:** Only the comment author can delete a comment (not the article author).

## Contract test suite

- [Hurl collection](https://github.com/realworld-apps/realworld/tree/main/specs/api/hurl) — mirrored locally at `specs/api/hurl/`
- Local test runner: `specs/api/hurl/run-hurl-tests.sh`

## Scope

- Stage 04b: exact response shapes in `spec.md` — every JSON response example in the OpenAPI spec must be reproduced in the per-scenario spec
- Stage 04c: contract-compliance scenarios in `.feature` files — flow tests must assert the exact response shapes and error envelopes
- Delivery: contract test tier in CI using the Hurl collection
