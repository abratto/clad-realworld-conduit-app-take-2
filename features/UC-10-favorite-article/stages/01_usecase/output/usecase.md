# UC-10 — Favorite Article

## Operational principle
A signed-in Member can favorite or unfavorite any article. Favoriting increments the article's favoritesCount and sets favorited=true. Unfavoriting reverses it.

## Scenarios

### Scenario: favorite-article
1. Member sends POST /api/articles/:slug/favorite with JWT.
2. System validates token, looks up article, records favorite.
3. System responds with HTTP 200 and updated article (favorited: true, favoritesCount incremented).

### Scenario: unfavorite-article  
1. Member sends DELETE /api/articles/:slug/favorite with JWT.
2. System validates token, looks up article, removes favorite.
3. System responds with HTTP 200 and updated article (favorited: false, favoritesCount decremented).

## Out of scope
- Viewing favorited articles — handled by UC-06 browse-articles with favorited filter.
