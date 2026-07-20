package com.conduit.app.syncs;

import com.conduit.app.concepts.user.UserConcept;
import com.conduit.app.engine.ActionLog;
import com.conduit.app.engine.FlowManager;
import com.conduit.app.engine.SyncAgent;
import com.conduit.app.engine.SyncMetadata;
import com.conduit.app.engine.SyncTrigger;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public final class WhenWebHandleRoutedThenUserLookupByUsernameForViewProfile extends SyncAgent {
    private static final String WEB_IRI = FlowManager.WEB_CONCEPT_IRI;
    private static final String PROFILES_ROUTE = "/api/profiles";
    @Inject public WhenWebHandleRoutedThenUserLookupByUsernameForViewProfile(ActionLog l) { super(l); }
    @Override public String syncName() { return "whenWebHandleRoutedThenUserLookupByUsernameForViewProfile"; }
    @Override public SyncTrigger trigger() { return new SyncTrigger(WEB_IRI, "handle", null); }
    @Override protected String whereClause() {
        return "?_when_1 :concept <%s> ; :name \"handle\" ; :input ?_handle_inp ; :flow ?_flow .\n?_handle_inp :username ?_username .\n?_req :concept <%s> ; :name \"request\" ; :flow ?_flow ; :input ?_req_inp .\n?_req_inp :route ?_route .".formatted(WEB_IRI, WEB_IRI);
    }
    @Override protected String thenBindings() {
        return "?_then_1 :concept <%s> ; :name \"lookupByUsername\" ; :input [ :username ?_username ] .".formatted(UserConcept.IRI);
    }
    @Override protected String parameterizeSparql(String sparql) {
        return bindLiteral(sparql, "_route", PROFILES_ROUTE);
    }
}
