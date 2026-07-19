# UC-13 — List Tags

## Operational principle
A Reader can retrieve all unique tags extracted from articles across the system. No authentication required.

## Scenario: list-tags
- **Trigger:** Reader requests tags.
- **Main flow:**
  1. Reader sends GET /api/tags.
  2. System collects all unique tag strings from articles.
  3. System responds with HTTP 200 and tags array.
- **Expected outcomes:** `{"tags": [...]}` with unique tag strings.
- **Postconditions:** No state modified.
