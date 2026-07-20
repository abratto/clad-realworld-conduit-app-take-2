package com.conduit.app.concepts.tag;
import com.conduit.app.engine.*;
import jakarta.inject.*;
import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.rdf.model.ResourceFactory;
import java.util.*;

@Singleton
public final class TagConcept extends ConceptAgent {
    public static final String IRI = "https://clad.dev/concept/tag";
    private static final String GRAPH = RdfVocabulary.conceptGraph("tag");
    private static final String NS = "https://clad.dev/concept/tag#";
    @Inject public TagConcept(ActionLog l, CompletionBus b) { super(l, b); }
    @Override protected String conceptIRI() { return IRI; }
    @Override public void pollAll() { pollAndProcess("list"); pollAndProcess("extract"); }
    @Override protected void processInvocation(ActionRecord inv) {
        switch (inv.actionName()) {
            case "list" -> doList(inv);
            case "extract" -> doExtract(inv);
            default -> writeError(inv, "unknown");
        }
    }
    private void doList(ActionRecord inv) {
        List<Map<String, String>> rows = actionLog.select(
            "PREFIX t: <" + NS + "> SELECT ?name WHERE { GRAPH <" + GRAPH + "> { ?s t:name ?name } }");
        String found = rows.isEmpty() ? "" : rows.get(0).get("name");
        writeCompletion(inv, Map.of("outcome", ResourceFactory.createStringLiteral("Listed")));
    }
    private void doExtract(ActionRecord inv) {
        String tagList = inv.binding("tagList");
        if (tagList == null || tagList.isBlank() || tagList.equals("[]")) {
            writeCompletion(inv, Map.of("outcome", ResourceFactory.createStringLiteral("Extracted")));
            return;
        }
        String cleaned = tagList.replaceAll("[\\[\\]\\s]", "");
        String[] tags = cleaned.split(",");
        var pss = new ParameterizedSparqlString();
        pss.setNsPrefix("t", NS);
        StringBuilder sparql = new StringBuilder("INSERT DATA { GRAPH <" + GRAPH + "> { ");
        for (int i = 0; i < tags.length; i++) {
            String tag = tags[i].trim().replaceAll("^\"|\"$", "");
            if (tag.isEmpty()) continue;
            String id = "t" + UUID.randomUUID().toString().substring(0, 8);
            sparql.append("<").append(NS).append(id).append("> t:name \"").append(tag.replace("\"", "\\\"")).append("\" . ");
        }
        sparql.append("} }");
        actionLog.update(sparql.toString());
        writeCompletion(inv, Map.of("outcome", ResourceFactory.createStringLiteral("Extracted")));
    }
}
