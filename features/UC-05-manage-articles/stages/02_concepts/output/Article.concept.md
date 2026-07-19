concept Article [ArticleId, UserId]
purpose
    to own article lifecycle — create, read, update, delete, and verify authorship

## State

```
slug:        ArticleId -> String    -- mandatory, unique, derived from title
title:       ArticleId -> String    -- mandatory
description: ArticleId -> String    -- mandatory
body:        ArticleId -> String    -- mandatory
authorId:    ArticleId -> UserId    -- mandatory
tagList:     ArticleId -> [String]  -- zero or more
createdAt:   ArticleId -> DateTime  -- mandatory
updatedAt:   ArticleId -> DateTime  -- mandatory
```

## Actions

```
create [ title: String ; description: String ; body: String ; tagList: [String] ; authorId: UserId ] => [ slug: String ]
    precondition { title not blank }
    postcondition { article created with all fields, slug generated from title }
    flow token: { action: "Article.create", title, authorId, slug, outcome: "Created" }

getBySlug [ slug: String ] => [ articleId: ArticleId ; authorId: UserId ; title: String ; description: String ; body: String ; tagList: [String] ; createdAt: DateTime ; updatedAt: DateTime ]
    precondition { slug exists }
    no state change
    flow token: { action: "Article.getBySlug", slug, outcome: "FOUND" }

update [ slug: String ; title?: String ; description?: String ; body?: String ] => [ Updated ]
    precondition { title not blank if provided ; slug exists }
    postcondition { article fields updated, updatedAt refreshed }
    flow token: { action: "Article.update", slug, outcome: "Updated" }

delete [ slug: String ] => [ Deleted ]
    precondition { slug exists }
    postcondition { article removed from state }
    flow token: { action: "Article.delete", slug, outcome: "Deleted" }

authorCheck [ articleId: ArticleId ; memberId: UserId ] => [ IsAuthor ]
    precondition { article exists }
    postcondition { memberId == authorId }
    no state change
    flow token: { action: "Article.authorCheck", articleId, memberId, outcome: "IsAuthor" }
```

## Notes

- Slug generation: kebab-case of title + unique suffix if needed.
- Precondition failures cause refusal — blank title, article not found, etc.
- `authorCheck` compares the requesting user to the article's authorId.
