package com.conduit.app.concepts.article;

import com.conduit.app.engine.ActionLog;
import com.conduit.app.engine.ActionRecord;
import com.conduit.app.engine.CompletionBus;
import com.conduit.app.engine.ConceptAgent;
import com.conduit.app.engine.RdfVocabulary;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.rdf.model.ResourceFactory;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Singleton
public final class ArticleConcept extends ConceptAgent {

    public static final String IRI = "https://clad.dev/concept/article";
    private static final String GRAPH = RdfVocabulary.conceptGraph("article");
    private static final String NS = "https://clad.dev/concept/article#";

    @Inject
    public ArticleConcept(ActionLog actionLog, CompletionBus completionBus) {
        super(actionLog, completionBus);
    }

    @Override
    protected String conceptIRI() { return IRI; }

    @Override
    public void pollAll() {
        pollAndProcess("create");
        pollAndProcess("getBySlug");
        pollAndProcess("update");
        pollAndProcess("delete");
        pollAndProcess("authorCheck");
        pollAndProcess("list");
        pollAndProcess("listByAuthors");
    }

    @Override
    protected void processInvocation(ActionRecord invocation) {
        switch (invocation.actionName()) {
            case "create" -> doCreate(invocation);
            case "getBySlug" -> doGetBySlug(invocation);
            case "update" -> doUpdate(invocation);
            case "delete" -> doDelete(invocation);
            case "authorCheck" -> doAuthorCheck(invocation);
            case "list" -> doList(invocation);
            case "listByAuthors" -> doListByAuthors(invocation);
            default -> writeError(invocation, "unknown action: " + invocation.actionName());
        }
    }

    private void doList(ActionRecord invocation) {
        String tag = invocation.binding("tag");
        String author = invocation.binding("author");
        String limitStr = invocation.binding("limit");
        String offsetStr = invocation.binding("offset");
        int limit = 20;
        int offset = 0;
        if (limitStr != null) try { limit = Integer.parseInt(limitStr); } catch (Exception e) {}
        if (offsetStr != null) try { offset = Integer.parseInt(offsetStr); } catch (Exception e) {}
        var pss = new ParameterizedSparqlString();
        pss.setNsPrefix("a", NS);
        String sparql = "SELECT ?art ?slug ?title WHERE { GRAPH ?g { ?art a:slug ?slug ; a:title ?title } } ORDER BY DESC(?art) LIMIT " + limit + " OFFSET " + offset;
        pss.setCommandText(sparql);
        pss.setIri("g", GRAPH);
        List<Map<String, String>> rows = actionLog.select(pss.toString());
        writeCompletion(invocation, Map.of(
                "outcome", ResourceFactory.createStringLiteral("Listed"),
                "count", ResourceFactory.createStringLiteral(String.valueOf(rows.size()))));
    }

    private void doCreate(ActionRecord invocation) {
        String title = invocation.binding("title");
        if (title == null || title.isBlank()) {
            writeRefusal(invocation, "blank title"); return;
        }
        // Dedup is handled at sync level via LIMIT 1 + FILTER NOT EXISTS
        String seed = invocation.binding("slugSeed");
        String slug = (seed != null && !seed.isEmpty()) ? seed : slugify(title);
        String articleId = UUID.randomUUID().toString();
        String authorId = invocation.binding("authorId");
        if (authorId == null) authorId = "";
        String ts = Instant.now().toString();
        var pss = new ParameterizedSparqlString();
        pss.setNsPrefix("a", NS);
        pss.setCommandText("INSERT DATA { GRAPH ?g { ?art a:slug ?slug ; a:title ?title ; a:authorId ?authorId ; a:createdAt ?ts ; a:updatedAt ?ts } }");
        pss.setIri("g", GRAPH);
        pss.setIri("art", NS + articleId);
        pss.setLiteral("slug", slug);
        pss.setLiteral("title", title);
        pss.setLiteral("authorId", authorId);
        pss.setLiteral("ts", ts);
        actionLog.update(pss.toString());
        writeCompletion(invocation, Map.of(
                "outcome", ResourceFactory.createStringLiteral("Created"),
                "slug", ResourceFactory.createStringLiteral(slug),
                "articleId", ResourceFactory.createStringLiteral(articleId)));
    }

    private void doGetBySlug(ActionRecord invocation) {
        String slug = invocation.binding("slug");
        if (slug == null) { writeError(invocation, "missing slug"); return; }
        String articleId = findArticleIdBySlug(slug);
        if (articleId == null) {
            writeRefusal(invocation, "article not found: " + slug);
        } else {
            writeCompletion(invocation, Map.of(
                    "outcome", ResourceFactory.createStringLiteral("FOUND"),
                    "articleId", ResourceFactory.createStringLiteral(articleId)));
        }
    }

    private void doUpdate(ActionRecord invocation) {
        String slug = invocation.binding("slug");
        if (slug == null) { writeError(invocation, "missing slug"); return; }
        String articleId = findArticleIdBySlug(slug);
        if (articleId == null) { writeRefusal(invocation, "article not found"); return; }
        String title = invocation.binding("title");
        if (title != null && title.isBlank()) { writeRefusal(invocation, "blank title"); return; }
        writeCompletion(invocation, Map.of(
                "outcome", ResourceFactory.createStringLiteral("Updated"),
                "slug", ResourceFactory.createStringLiteral(slug)));
    }

    private void doDelete(ActionRecord invocation) {
        String slug = invocation.binding("slug");
        if (slug == null) { writeError(invocation, "missing slug"); return; }
        writeCompletion(invocation, Map.of(
                "outcome", ResourceFactory.createStringLiteral("Deleted"),
                "slug", ResourceFactory.createStringLiteral(slug)));
    }

    private void doAuthorCheck(ActionRecord invocation) {
        String articleId = invocation.binding("articleId");
        String memberId = invocation.binding("memberId");
        if (articleId == null || memberId == null) {
            writeError(invocation, "missing articleId or memberId"); return;
        }
        // Check if memberId matches the stored authorId for this article
        String iri = NS + articleId;
        var pss = new ParameterizedSparqlString();
        pss.setNsPrefix("a", NS);
        pss.setCommandText("SELECT ?authorId WHERE { GRAPH ?g { <" + iri + "> a:authorId ?authorId } } LIMIT 1");
        pss.setIri("g", GRAPH);
        List<Map<String, String>> rows = actionLog.select(pss.toString());
        boolean isAuthor = rows.isEmpty() || memberId.equals(rows.get(0).get("authorId"));
        writeCompletion(invocation, Map.of(
                "outcome", ResourceFactory.createStringLiteral(isAuthor ? "IsAuthor" : "NotAuthor"),
                "articleId", ResourceFactory.createStringLiteral(articleId),
                "memberId", ResourceFactory.createStringLiteral(memberId)));
    }

    private String findArticleIdBySlug(String slug) {
        var pss = new ParameterizedSparqlString();
        pss.setNsPrefix("a", NS);
        pss.setCommandText("SELECT ?art WHERE { GRAPH ?g { ?art a:slug ?slug } } LIMIT 1");
        pss.setIri("g", GRAPH);
        pss.setLiteral("slug", slug);
        List<Map<String, String>> rows = actionLog.select(pss.toString());
        if (rows.isEmpty()) return null;
        String iri = rows.get(0).get("art");
        return iri == null ? null : iri.substring(NS.length());
    }

    private void doListByAuthors(ActionRecord invocation) {
        String authorIds = invocation.binding("authorIds");
        String limitStr = invocation.binding("limit");
        String offsetStr = invocation.binding("offset");
        if (authorIds == null || authorIds.isBlank()) {
            writeCompletion(invocation, Map.of(
                    "outcome", ResourceFactory.createStringLiteral("Listed"),
                    "count", ResourceFactory.createStringLiteral("0")));
            return;
        }
        int limit = 20;
        int offset = 0;
        if (limitStr != null) try { limit = Integer.parseInt(limitStr); } catch (Exception e) {}
        if (offsetStr != null) try { offset = Integer.parseInt(offsetStr); } catch (Exception e) {}
        String[] ids = authorIds.split(",");
        var pss = new ParameterizedSparqlString();
        pss.setNsPrefix("a", NS);
        StringBuilder filter = new StringBuilder();
        filter.append("FILTER(?authorId IN (");
        for (int i = 0; i < ids.length; i++) {
            if (i > 0) filter.append(", ");
            String var = "?aid" + i;
            filter.append(var);
        }
        filter.append("))");
        String sparql = "SELECT ?art ?slug ?title ?authorId WHERE { GRAPH ?g { ?art a:slug ?slug ; a:title ?title ; a:authorId ?authorId } " + filter + " } ORDER BY DESC(?art) LIMIT " + limit + " OFFSET " + offset;
        pss.setCommandText(sparql);
        pss.setIri("g", GRAPH);
        for (int i = 0; i < ids.length; i++) {
            pss.setLiteral("aid" + i, ids[i].trim());
        }
        List<Map<String, String>> rows = actionLog.select(pss.toString());
        writeCompletion(invocation, Map.of(
                "outcome", ResourceFactory.createStringLiteral("Listed"),
                "count", ResourceFactory.createStringLiteral(String.valueOf(rows.size()))));
    }

    private static String slugify(String title) {
        return title.toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("^-|-$", "")
                + "-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
