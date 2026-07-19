concept Article [ArticleId]
## Actions
```
listByAuthors [ authorIds: [UserId] ; limit: Int ; offset: Int ] => [ Listed(articles, count) ]
    returns articles by specified authors, sorted by createdAt desc, paginated
    no state change
    flow token: { action: "Article.listByAuthors", authorIds, limit, offset, outcome: "Listed" }
```
