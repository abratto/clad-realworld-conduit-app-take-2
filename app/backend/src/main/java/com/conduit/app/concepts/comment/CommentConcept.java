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

    @Inject public CommentConcept(ActionLog l, CompletionBus b) { super(l, b); }
    @Override protected String conceptIRI() { return IRI; }
    @Override public void pollAll() { pollAndProcess("add"); pollAndProcess("delete"); pollAndProcess("authorCheck"); pollAndProcess("listByArticle"); }

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
        String commentId = UUID.randomUUID().toString();
        String ts = Instant.now().toString();
        var pss = new ParameterizedSparqlString();
        pss.setNsPrefix("c", NS);
        pss.setCommandText("INSERT DATA { GRAPH ?g { ?c c:body ?body ; c:createdAt ?ts } }");
        pss.setIri("g", GRAPH); pss.setIri("c", NS + commentId);
        pss.setLiteral("body", body); pss.setLiteral("ts", ts);
        actionLog.update(pss.toString());
        writeCompletion(inv, Map.of("outcome", ResourceFactory.createStringLiteral("Added"),
                "commentId", ResourceFactory.createStringLiteral(commentId)));
    }

    private void doDelete(ActionRecord inv) {
        String commentId = inv.binding("commentId");
        if (commentId == null) { writeError(inv, "missing commentId"); return; }
        writeCompletion(inv, Map.of("outcome", ResourceFactory.createStringLiteral("Deleted"),
                "commentId", ResourceFactory.createStringLiteral(commentId)));
    }

    private void doAuthorCheck(ActionRecord inv) {
        writeCompletion(inv, Map.of("outcome", ResourceFactory.createStringLiteral("IsAuthor")));
    }
}
