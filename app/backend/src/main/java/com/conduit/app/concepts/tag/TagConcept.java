package com.conduit.app.concepts.tag;
import com.conduit.app.engine.*;
import jakarta.inject.*;
import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.rdf.model.ResourceFactory;
import java.util.*;

@Singleton
public final class TagConcept extends ConceptAgent {
    public static final String IRI = "https://clad.dev/concept/tag";
    @Inject public TagConcept(ActionLog l, CompletionBus b) { super(l, b); }
    @Override protected String conceptIRI() { return IRI; }
    @Override public void pollAll() { pollAndProcess("list"); }
    @Override protected void processInvocation(ActionRecord inv) {
        if ("list".equals(inv.actionName())) {
            writeCompletion(inv, Map.of("outcome", ResourceFactory.createStringLiteral("Listed")));
        } else {
            writeError(inv, "unknown");
        }
    }
}
