package com.conduit.app.concepts.follow;

import com.conduit.app.engine.*;
import jakarta.inject.*;
import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.rdf.model.ResourceFactory;
import java.util.*;
@Singleton
public final class FollowConcept extends ConceptAgent {
    public static final String IRI = "https://clad.dev/concept/follow";
    private static final String GRAPH = RdfVocabulary.conceptGraph("follow");
    private static final String NS = "https://clad.dev/concept/follow#";
    @Inject public FollowConcept(ActionLog l, CompletionBus b) { super(l, b); }
    @Override protected String conceptIRI() { return IRI; }
    @Override public void pollAll() { pollAndProcess("follow"); pollAndProcess("unfollow"); pollAndProcess("getFollowedUsers"); pollAndProcess("isFollowing"); }
    @Override protected void processInvocation(ActionRecord inv) {
        switch (inv.actionName()) {
            case "follow" -> doFollow(inv);
            case "unfollow" -> doUnfollow(inv);
            case "getFollowedUsers" -> doGetFollowedUsers(inv);
            case "isFollowing" -> doIsFollowing(inv);
            default -> writeError(inv, "unknown");
        }
    }
    private void doFollow(ActionRecord inv) {
        String followerId = inv.binding("followerId");
        String followeeId = inv.binding("followeeId");
        if (followerId == null || followeeId == null) { writeRefusal(inv, "missing ids"); return; }
        var pss = new ParameterizedSparqlString();
        pss.setNsPrefix("f", NS);
        pss.setCommandText("INSERT DATA { GRAPH ?g { ?sub f:follower ?follower ; f:followee ?followee } }");
        pss.setIri("g", GRAPH);
        pss.setIri("sub", NS + UUID.randomUUID());
        pss.setIri("follower", NS + followerId);
        pss.setIri("followee", NS + followeeId);
        actionLog.update(pss.toString());
        writeCompletion(inv, Map.of("outcome", ResourceFactory.createStringLiteral("Followed")));
    }
    private void doUnfollow(ActionRecord inv) {
        String followerId = inv.binding("followerId");
        String followeeId = inv.binding("followeeId");
        if (followerId == null || followeeId == null) { writeRefusal(inv, "missing ids"); return; }
        var pss = new ParameterizedSparqlString();
        pss.setNsPrefix("f", NS);
        pss.setCommandText("DELETE { GRAPH ?g { ?sub f:follower ?follower ; f:followee ?followee } } WHERE { GRAPH ?g { ?sub f:follower ?follower ; f:followee ?followee } }");
        pss.setIri("g", GRAPH);
        pss.setIri("follower", NS + followerId);
        pss.setIri("followee", NS + followeeId);
        actionLog.update(pss.toString());
        writeCompletion(inv, Map.of("outcome", ResourceFactory.createStringLiteral("Unfollowed")));
    }
    private void doGetFollowedUsers(ActionRecord inv) {
        String userId = inv.binding("userId");
        if (userId == null) { writeRefusal(inv, "missing userId"); return; }
        var pss = new ParameterizedSparqlString();
        pss.setNsPrefix("f", NS);
        pss.setCommandText("SELECT ?followee WHERE { GRAPH ?g { ?sub f:follower ?follower ; f:followee ?followee } }");
        pss.setIri("g", GRAPH);
        pss.setIri("follower", NS + userId);
        List<Map<String, String>> rows = actionLog.select(pss.toString());
        StringBuilder sb = new StringBuilder();
        for (var row : rows) {
            String iri = row.get("followee");
            if (iri != null && iri.startsWith(NS)) {
                if (sb.length() > 0) sb.append(",");
                sb.append(iri.substring(NS.length()));
            }
        }
        String ids = sb.toString();
        writeCompletion(inv, Map.of(
                "outcome", ResourceFactory.createStringLiteral("Listed"),
                "followeeIds", ResourceFactory.createStringLiteral(ids)));
    }
    private void doIsFollowing(ActionRecord inv) {
        writeCompletion(inv, Map.of("outcome", ResourceFactory.createStringLiteral("True")));
    }
}
