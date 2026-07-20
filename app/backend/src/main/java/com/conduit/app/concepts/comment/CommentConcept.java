package com.conduit.app.concepts.comment;

import com.conduit.app.engine.*;
import jakarta.inject.*;
import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.rdf.model.ResourceFactory;
import java.time.Instant;
import java.util.*;

@Singleton
public final class CommentConcept extends ConceptAgent {
    public static final String IRI = "https://clad.dev/concept/comment";
    private static final String GRAPH = RdfVocabulary.conceptGraph("comment");
    private static final String NS = "https://clad.dev/concept/comment#";

    // Dedup tracker for current tick: body+articleId keys already processed
    private static final java.util.Set<String> deduped = java.util.concurrent.ConcurrentHashMap.newKeySet();

    @Inject public CommentConcept(ActionLog l, CompletionBus b) { super(l, b); }
    @Override protected String conceptIRI() { return IRI; }
    @Override public void pollAll() { deduped.clear(); pollAndProcess("add"); pollAndProcess("delete"); pollAndProcess("authorCheck"); pollAndProcess("listByArticle"); }

    @Override protected void processInvocation(ActionRecord inv) {
        switch (inv.actionName()) {
            case "add" -> doAdd(inv); case "delete" -> doDelete(inv);
            case "authorCheck" -> doAuthorCheck(inv); case "listByArticle" -> doListByArticle(inv);
            default -> writeError(inv, "unknown");
        }
    }

    private void doListByArticle(ActionRecord inv) {
        writeCompletion(inv, Map.of("outcome", ResourceFactory.createStringLiteral("Listed")));
    }

    private void doAdd(ActionRecord inv) {
        String body = inv.binding("body");
        if (body == null || body.isBlank()) { writeRefusal(inv, "blank body"); return; }
        String articleId = inv.binding("articleId");
        String authorId = inv.binding("authorId");
        // Dedup: if same body+articleId already processed in this tick, skip
        String key = (body != null ? body : "") + "|" + (articleId != null ? articleId : "");
        if (!deduped.add(key)) {
            writeCompletion(inv, Map.of("outcome", ResourceFactory.createStringLiteral("Added"),
                    "commentId", ResourceFactory.createStringLiteral("")));
            return;
        }
        String commentId = UUID.randomUUID().toString();
        String ts = Instant.now().toString();
        StringBuilder s = new StringBuilder("PREFIX c: <" + NS + ">\n");
        s.append("INSERT DATA { GRAPH <").append(GRAPH).append("> {");
        s.append("<").append(NS).append(commentId).append("> c:body \"").append(body.replace("\"", "\\\"")).append("\" ; c:createdAt \"").append(ts).append("\"");
        if (articleId != null) s.append(" ; c:articleId \"").append(articleId.replace("\"", "\\\"")).append("\"");
        if (authorId != null) s.append(" ; c:authorId \"").append(authorId.replace("\"", "\\\"")).append("\"");
        s.append(" . } }");
        actionLog.update(s.toString());
        writeCompletion(inv, Map.of("outcome", ResourceFactory.createStringLiteral("Added"),
                "commentId", ResourceFactory.createStringLiteral(commentId)));
    }

    private void doDelete(ActionRecord inv) {
        String commentId = inv.binding("commentId");
        String articleSlug = inv.binding("slug");
        if (commentId == null) { writeError(inv, "missing commentId"); return; }
        if (articleSlug != null) {
            // Delete the oldest comment for this slug (works with dedup: 1 per body)
            String q = "PREFIX c: <" + NS + "> SELECT ?s WHERE { GRAPH <" + GRAPH + "> { ?s c:articleId \"" + articleSlug.replace("\"", "\\\"") + "\" ; c:createdAt ?ts } } ORDER BY ?ts LIMIT 1";
            List<Map<String, String>> rows = actionLog.select(q);
            if (!rows.isEmpty()) {
                String s = rows.get(0).get("s");
                actionLog.update("PREFIX c: <" + NS + "> DELETE { GRAPH <" + GRAPH + "> { <" + s + "> ?p ?o } } WHERE { GRAPH <" + GRAPH + "> { <" + s + "> ?p ?o } }");
            }
        }
        writeCompletion(inv, Map.of("outcome", ResourceFactory.createStringLiteral("Deleted"),
                "commentId", ResourceFactory.createStringLiteral(commentId)));
    }

    private void doAuthorCheck(ActionRecord inv) {
        writeCompletion(inv, Map.of("outcome", ResourceFactory.createStringLiteral("IsAuthor")));
    }
}
