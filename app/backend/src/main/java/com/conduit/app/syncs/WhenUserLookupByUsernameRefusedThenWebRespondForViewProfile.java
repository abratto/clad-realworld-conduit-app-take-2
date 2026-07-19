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
public final class WhenUserLookupByUsernameRefusedThenWebRespondForViewProfile extends SyncAgent {
    private static final String WEB_IRI = FlowManager.WEB_CONCEPT_IRI;
    private static final String PROFILE_ROUTE = "profile";
    @Inject public WhenUserLookupByUsernameRefusedThenWebRespondForViewProfile(ActionLog l) { super(l); }
    @Override public String syncName() { return "whenUserLookupByUsernameRefusedThenWebRespondForViewProfile"; }
    @Override public SyncTrigger trigger() { return new SyncTrigger(UserConcept.IRI, "lookupByUsername", null); }
    @Override protected String whereClause() {
        return "?_when_1 :concept <%s> ; :name \"lookupByUsername\" ; :flow ?_flow .\n<< ?_when_1 :outcome \"refused\" >> :flow ?_flow .\n?_req :concept <%s> ; :name \"request\" ; :flow ?_flow ; :input ?_inp .\n?_inp :route ?_route .".formatted(UserConcept.IRI, FlowManager.WEB_CONCEPT_IRI);
    }
    @Override protected String thenBindings() {
        return "?_then_1 :concept <%s> ; :name \"respond\" ; :input [ :statusCode 404 ; :message \"profile not found\" ] .".formatted(WEB_IRI);
    }
    @Override protected String parameterizeSparql(String sparql) {
        return bindLiteral(sparql, "_route", PROFILE_ROUTE);
    }
}
