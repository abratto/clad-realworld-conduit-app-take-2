# Actors

| Name | Role | Primary concerns |
|---|---|---|
| Reader | Anyone who browses or discovers public content — may be unauthenticated or an authenticated Member reading | Browse articles by tag/author/favorited status, read article details by slug, view profiles, see comments on articles, list tags |
| Member | Authenticated user who owns content and manages social relationships | Register account, sign in, view/update own profile, create/update/delete own articles, add/delete own comments, favorite/unfavorite articles, follow/unfollow other members, view personalized feed |

## Notes

A Member is always also a Reader — the two roles overlap, but the concerns are kept separate so Reader-only goals (browsing, discovery) do not depend on authentication, while Member goals add authenticated write operations on top.

The System actor is intentionally absent. System responsibilities (session management, slug generation, feed computation, ownership checks) emerge as concepts and syncs during Stages 01–03, not as a separate actor with its own goals.
