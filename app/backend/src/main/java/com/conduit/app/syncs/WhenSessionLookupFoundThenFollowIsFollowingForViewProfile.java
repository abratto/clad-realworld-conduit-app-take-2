package com.conduit.app.syncs;

import com.conduit.app.concepts.session.SessionConcept;
import com.conduit.app.concepts.user.UserConcept;
import com.conduit.app.engine.ActionLog;
import com.conduit.app.engine.FlowManager;
import com.conduit.app.engine.SyncAgent;
import com.conduit.app.engine.SyncMetadata;
import com.conduit.app.engine.SyncTrigger;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public final class WhenSessionLookupFoundThenFollowIsFollowingForViewProfile extends SyncAgent {
    private static final String WEB_IRI = FlowManager.WEB_CONCEPT_IRI;
    private static final String PROFILE_ROUTE = "/api/profiles";
    @Inject public WhenSessionLookupFoundThenFollowIsFollowingForViewProfile(ActionLog l) { super(l); }
    @Override public String syncName() { return "whenSessionLookupFoundThenFollowIsFollowingForViewProfile"; }
    @Override public SyncTrigger trigger() { return new SyncTrigger(SessionConcept.IRI, "lookup", null); }
    @Override protected String whereClause() {
        return "?_when_1 :concept <%s> ; :name \"lookup\" ; :flow ?_flow ; :userId ?_viewerId .\n<< ?_when_1 :outcome \"FOUND\" >> :flow ?_flow .\n?_req :concept <%s> ; :name \"request\" ; :flow ?_flow ; :input ?_inp .\n?_inp :route ?_route .".formatted(SessionConcept.IRI, WEB_IRI);
    }
    @Override protected String thenBindings() {
        return "?_then_1 :concept <https://clad.dev/concept/follow> ; :name \"isFollowing\" ; :input [ :followerId ?_viewerId ] .".formatted();
    }
    @Override protected String parameterizeSparql(String sparql) {
        return bindLiteral(sparql, "_route", PROFILE_ROUTE);
    }
}
