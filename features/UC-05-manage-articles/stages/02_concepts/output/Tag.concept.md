concept Tag
purpose
    to extract and index tags from article tagLists

## State

```
*None.* Tag is stateless — tags are derived from article tagList fields.
```

## Actions

```
extract [ tagList: [String] ] => [ Extracted ]
    records each tag for later retrieval by Tag.list
    flow token: { action: "Tag.extract", tagList, outcome: "Extracted" }
```

## Notes

- Tag is lightweight in this UC — full tag management is in UC-13-list-tags.
- Tags are strings extracted from articles during creation.
