# Article — conceptual data model

Step 1: Article has slug "my-title", title "My Title", description "...", body "...", author ada.
Facts: Article has slug (unique), title, description, body, authorId, tagList, createdAt, updatedAt.

Step 2: Entity: Article (ArticleId). 8 fact types.

Step 3-7: slug unique. title, description, body, authorId, createdAt, updatedAt mandatory. tagList zero or more.
