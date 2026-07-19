concept Article [ArticleId]
purpose to list articles with filters and pagination

## State
```
slug: ArticleId -> String; title, description, body: ArticleId -> String
authorId: ArticleId -> UserId; tagList: ArticleId -> [String]
createdAt, updatedAt: ArticleId -> DateTime
```

## Actions
```
list [ tag?: String ; author?: String ; favorited?: String ; limit: Int ; offset: Int ] => [ Listed(articles, count) ]
    returns articles matching filters, sorted by createdAt desc, paginated
    no state change
    flow token: { action: "Article.list", filters, limit, offset, outcome: "Listed" }
```
