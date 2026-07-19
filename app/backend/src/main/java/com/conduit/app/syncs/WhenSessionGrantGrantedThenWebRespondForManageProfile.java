package com.conduit.app.syncs;

import com.conduit.app.concepts.session.SessionConcept;
import com.conduit.app.engine.ActionLog;
import com.conduit.app.engine.FlowManager;
import com.conduit.app.engine.SyncAgent;
import com.conduit.app.engine.SyncMetadata;
import com.conduit.app.engine.SyncTrigger;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@SyncMetadata(flow = "Profile", step = 6, triggeredBy = "Session/grant[Granted]", fires = "Web/respond[200]")
@Singleton
public final class WhenSessionGrantGrantedThenWebRespondForManageProfile extends SyncAgent {
    private static final String WEB_IRI = FlowManager.WEB_CONCEPT_IRI;
    @Inject public WhenSessionGrantGrantedThenWebRespondForManageProfile(ActionLog l) { super(l); }
    @Override public String syncName() { return "whenSessionGrantGrantedThenWebRespondForManageProfile"; }
    @Override public SyncTrigger trigger() { return new SyncTrigger(SessionConcept.IRI, "grant", null); }
    @Override protected String whereClause() {
        return """
            ?_when_1 :concept <%s> ; :name "grant" ; :userId ?_userId ; :sessionToken ?_token ; :flow ?_flow .
            << ?_when_1 :outcome "Granted" >> :flow ?_flow .
            ?_req :concept <%s> ; :name "request" ; :flow ?_flow ; :input ?_inp .
            ?_inp :token ?_auth_token .
            """.formatted(SessionConcept.IRI, WEB_IRI);
    }
    @Override protected String thenBindings() {
        return "?_then_1 :concept <%s> ; :name \"respond\" ; :input [ :statusCode 200 ; :token ?_token ; :userId ?_userId ] .".formatted(WEB_IRI);
    }
}
