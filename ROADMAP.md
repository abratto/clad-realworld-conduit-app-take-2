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
| 2 | Conduit backend — core | 13 UCs (UC-01–UC-13) | doing | Building full Conduit/RealWorld backend |

## Backlog

> Use cases identified but not yet promoted into a phase. Promote a row
> by moving it into the phases table and setting status to `doing`
> (and demoting the previous `doing` row to `done`).

- _full list is the 13 in-scope goals from Stage 00, sequenced in order below_

## Resume point

> Updated at the end of every working session.

- **Last gate passed:** Stage 00 (system-level actors/goals/port-spec) — 2026-07-18
- **Current feature:** UC-01-register — next
- **Blockers:** none
- **Last updated:** 2026-07-18 — Stage 00 approved; 13 in-scope goals identified
