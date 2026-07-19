package com.conduit.app.syncs;
import com.conduit.app.concepts.session.SessionConcept;
import com.conduit.app.engine.*;
import jakarta.inject.*;
@Singleton
public final class WhenSessionLookupFoundThenFollowGetFollowedUsersForViewFeed extends SyncAgent {
    private static final String WEB_IRI = FlowManager.WEB_CONCEPT_IRI;
    @Inject public WhenSessionLookupFoundThenFollowGetFollowedUsersForViewFeed(ActionLog l) { super(l); }
    @Override public String syncName() { return "whenSessionLookupFoundThenFollowGetFollowedUsersForViewFeed"; }
    @Override public SyncTrigger trigger() { return new SyncTrigger(SessionConcept.IRI, "lookup", null); }
    @Override protected String whereClause() {
        return "?_when_1 :concept <%s> ; :name \"lookup\" ; :flow ?_flow ; :userId ?_userId .\n<< ?_when_1 :outcome \"FOUND\" >> :flow ?_flow .\n?_req :concept <%s> ; :name \"request\" ; :flow ?_flow ; :input ?_inp .\n?_inp :route ?_route .".formatted(SessionConcept.IRI, WEB_IRI);
    }
    @Override protected String thenBindings() {
        return "?_then_1 :concept <https://clad.dev/concept/follow> ; :name \"getFollowedUsers\" ; :input [ :userId ?_userId ] .";
    }
    @Override protected String parameterizeSparql(String sparql) {
        return bindLiteral(sparql, "_route", "/api/articles/feed");
    }
}
