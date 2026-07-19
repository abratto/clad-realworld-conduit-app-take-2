package com.conduit.app.concepts.favorite;
import com.conduit.app.engine.*;
import jakarta.inject.*;
import org.apache.jena.rdf.model.ResourceFactory;
import java.util.*;

@Singleton
public final class FavoriteConcept extends ConceptAgent {
    public static final String IRI = "https://clad.dev/concept/favorite";
    @Inject public FavoriteConcept(ActionLog l, CompletionBus b) { super(l, b); }
    @Override protected String conceptIRI() { return IRI; }
    @Override public void pollAll() { pollAndProcess("favorite"); pollAndProcess("unfavorite"); }
    @Override protected void processInvocation(ActionRecord inv) {
        switch (inv.actionName()) {
            case "favorite" -> writeCompletion(inv, Map.of("outcome", ResourceFactory.createStringLiteral("Favorited")));
            case "unfavorite" -> writeCompletion(inv, Map.of("outcome", ResourceFactory.createStringLiteral("Unfavorited")));
            default -> writeError(inv, "unknown");
        }
    }
}
