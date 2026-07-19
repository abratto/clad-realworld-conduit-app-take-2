concept Tag
purpose to list all unique tags
## Actions
```
list [] => [ Listed(tags) ]
    returns all unique tag strings from articles
    no state change
    flow token: { action: "Tag.list", outcome: "Listed" }
```
