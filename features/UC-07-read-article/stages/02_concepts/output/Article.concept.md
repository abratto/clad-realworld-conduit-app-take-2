concept Article [ArticleId]
purpose to retrieve a single article by slug

## State slug: ArticleId -> String (unique); title, description, body: ArticleId -> String; authorId: ArticleId -> UserId; tagList: ArticleId -> [String]; createdAt, updatedAt: ArticleId -> DateTime

## Actions
```
getBySlug [ slug: String ] => [ articleId: ArticleId ; title: String ; description: String ; body: String ; tagList: [String] ; authorId: UserId ; createdAt: DateTime ; updatedAt: DateTime ]
    precondition { slug exists }
    no state change
    flow token: { action: "Article.getBySlug", slug, outcome: "FOUND" }
```
