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
    }

    @Override
    protected void processInvocation(ActionRecord invocation) {
        switch (invocation.actionName()) {
            case "create" -> doCreate(invocation);
            case "getBySlug" -> doGetBySlug(invocation);
            case "update" -> doUpdate(invocation);
            case "delete" -> doDelete(invocation);
            case "authorCheck" -> doAuthorCheck(invocation);
            default -> writeError(invocation, "unknown action: " + invocation.actionName());
        }
    }

    private void doCreate(ActionRecord invocation) {
        String title = invocation.binding("title");
        if (title == null || title.isBlank()) {
            writeRefusal(invocation, "blank title"); return;
        }
        String slug = slugify(title);
        String articleId = UUID.randomUUID().toString();
        String ts = Instant.now().toString();
        var pss = new ParameterizedSparqlString();
        pss.setNsPrefix("a", NS);
        pss.setCommandText("INSERT DATA { GRAPH ?g { ?art a:slug ?slug ; a:title ?title ; a:createdAt ?ts ; a:updatedAt ?ts } }");
        pss.setIri("g", GRAPH);
        pss.setIri("art", NS + articleId);
        pss.setLiteral("slug", slug);
        pss.setLiteral("title", title);
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
        writeCompletion(invocation, Map.of(
                "outcome", ResourceFactory.createStringLiteral("IsAuthor"),
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

    private static String slugify(String title) {
        return title.toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("^-|-$", "")
                + "-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
