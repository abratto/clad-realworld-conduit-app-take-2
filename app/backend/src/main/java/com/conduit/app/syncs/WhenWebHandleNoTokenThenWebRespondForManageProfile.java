package com.conduit.app.syncs;

import com.conduit.app.engine.ActionLog;
import com.conduit.app.engine.FlowManager;
import com.conduit.app.engine.SyncAgent;
import com.conduit.app.engine.SyncMetadata;
import com.conduit.app.engine.SyncTrigger;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@SyncMetadata(flow = "Profile", step = 2, triggeredBy = "Web/handle[Refused:noToken]", fires = "Web/respond[401]")
@Singleton
public final class WhenWebHandleNoTokenThenWebRespondForManageProfile extends SyncAgent {
    private static final String WEB_IRI = FlowManager.WEB_CONCEPT_IRI;
    private static final String PROFILE_ROUTE = "profile";
    @Inject public WhenWebHandleNoTokenThenWebRespondForManageProfile(ActionLog l) { super(l); }
    @Override public String syncName() { return "whenWebHandleNoTokenThenWebRespondForManageProfile"; }
    @Override public SyncTrigger trigger() { return new SyncTrigger(WEB_IRI, "handle", null); }
    @Override protected String whereClause() {
        return "?_when_1 :concept <%s> ; :name \"handle\" ; :flow ?_flow .\n<< ?_when_1 :outcome \"refused\" >> :flow ?_flow .\n?_req :concept <%s> ; :name \"request\" ; :flow ?_flow ; :input ?_req_inp .\n?_req_inp :route ?_route .".formatted(WEB_IRI, WEB_IRI);
    }
    @Override protected String thenBindings() {
        return "?_then_1 :concept <%s> ; :name \"respond\" ; :input [ :statusCode 401 ; :message \"token is missing\" ] .".formatted(WEB_IRI);
    }
    @Override protected String parameterizeSparql(String sparql) {
        return bindLiteral(sparql, "_route", PROFILE_ROUTE);
    }
}
