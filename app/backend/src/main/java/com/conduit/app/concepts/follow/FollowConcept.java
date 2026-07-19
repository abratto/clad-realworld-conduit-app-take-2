package com.conduit.app.concepts.follow;
import com.conduit.app.engine.*;
import jakarta.inject.*;
import org.apache.jena.rdf.model.ResourceFactory;
import java.util.*;
@Singleton
public final class FollowConcept extends ConceptAgent {
    public static final String IRI = "https://clad.dev/concept/follow";
    @Inject public FollowConcept(ActionLog l, CompletionBus b) { super(l, b); }
    @Override protected String conceptIRI() { return IRI; }
    @Override public void pollAll() { pollAndProcess("follow"); pollAndProcess("unfollow"); }
    @Override protected void processInvocation(ActionRecord inv) {
        switch (inv.actionName()) {
            case "follow" -> writeCompletion(inv, Map.of("outcome", ResourceFactory.createStringLiteral("Followed")));
            case "unfollow" -> writeCompletion(inv, Map.of("outcome", ResourceFactory.createStringLiteral("Unfollowed")));
            default -> writeError(inv, "unknown");
        }
    }
}
