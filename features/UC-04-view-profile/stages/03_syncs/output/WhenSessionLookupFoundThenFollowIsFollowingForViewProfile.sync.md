sync WhenSessionLookupFoundThenFollowIsFollowingForViewProfile

## Sync Contract Matrix

| Source row | Target row | `when` signature | `then` signature | Allowed literals |
|---|---|---|---|---|
| 5a | 6a | `Session/lookup: [ token: ?_ ] => [ outcome: "FOUND" ; userId: ?viewerId ]` | `Follow/isFollowing: [ followerId: ?viewerId ; profileId: ?profileId ]` | none |

## Rule

```
when { Session/lookup: [ token: ?_ ] => [ outcome: "FOUND" ; userId: ?viewerId ] }
where {
    A: bind (?viewerId as ?followerId)
    B: bind (?profileId as ?profileId)
}
then { Follow/isFollowing: [ followerId: ?followerId ; profileId: ?profileId ] }
```

## Cites

- ../01_usecase/output/usecase.md — scenario "view-profile", main flow step 3
