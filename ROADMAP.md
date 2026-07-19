<!-- See methodology/overlays/TRACKING.md for the conventions this file follows. -->

# Roadmap

> One row per phase. **Exactly one** phase has status `doing` at any time;
> everything else is `done`, `next`, or `later`. The `doing` phase points
> at the active feature folder under `features/`.
>
> CI enforces: at most one `doing` row, and a current `Resume point` block.
> See [`.github/scripts/check-roadmap-hygiene.sh`](.github/scripts/check-roadmap-hygiene.sh).

## Phases

| # | Phase | Feature(s) | Status | Notes |
|---|---|---|---|---|---|
| 1 | Seed methodology | `UC-00-login` | done | Worked example, end-to-end through Stage 04. |
| 2 | Conduit backend — core | UC-01-register | done | Registration complete |
| 3 | Conduit backend — auth | UC-02-sign-in | done | Email-based sign-in complete |
| 4 | Conduit backend — profile | UC-03-manage-profile | done | View & update profile (71 tests) |
| 5 | Conduit backend — public profile | UC-04-view-profile | done | Public profile with following indicator |
| 6 | Conduit backend — articles | UC-05-manage-articles | done | Article CRUD (73 tests) |
| 7 | Conduit backend — browse | UC-06-browse-articles | done | Browse articles with pagination |
| 8 | Conduit backend — reading | UC-07-read-article | done | Read article by slug |
| 9 | Conduit backend — comments | UC-08-comment-on-article | done | Add/delete comments |
| 10 | Conduit backend — comments view | UC-09-view-comments | done | View article comments |
| 11 | Conduit backend — favorites | UC-10-favorite-article | next | Favorite/unfavorite articles |

## Backlog

> Use cases identified but not yet promoted into a phase. Promote a row
> by moving it into the phases table and setting status to `doing`
> (and demoting the previous `doing` row to `done`).

- _full list is the 13 in-scope goals from Stage 00, sequenced in order below_

## Resume point

> Updated at the end of every working session.

- **Last gate passed:** UC-01-register Stage 05 (verify) — 2026-07-18
- **Current feature:** UC-02-sign-in — next
- **Blockers:** none
- **Last updated:** 2026-07-18 — UC-01-register complete; 66 tests green
