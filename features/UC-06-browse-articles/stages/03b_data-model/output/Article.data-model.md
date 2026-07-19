# Article — conceptual data model
Step 1: Article "my-title" has title "My Title", author ada, tags ["prog"].
Facts: slug unique, title/desc/body mandatory, tagList zero+, timestamps.

Step 2: Entity: Article (ArticleId). 7 fact types.

Step 3-7: slug unique. Listing returns subset filtered by tag/author/favorited.
