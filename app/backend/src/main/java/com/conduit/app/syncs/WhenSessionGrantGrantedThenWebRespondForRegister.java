package com.conduit.app.syncs;

import com.conduit.app.concepts.session.SessionConcept;
import com.conduit.app.engine.ActionLog;
import com.conduit.app.engine.FlowManager;
import com.conduit.app.engine.SyncAgent;
import com.conduit.app.engine.SyncMetadata;
import com.conduit.app.engine.SyncTrigger;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@SyncMetadata(
        flow = "Register",
        step = 7,
        triggeredBy = "Session/grant[Granted]",
        fires = "Web/respond[201]")
@Singleton
public final class WhenSessionGrantGrantedThenWebRespondForRegister extends SyncAgent {

    private static final String WEB_IRI = FlowManager.WEB_CONCEPT_IRI;
    private static final String SESSION_IRI = SessionConcept.IRI;

    @Inject
    public WhenSessionGrantGrantedThenWebRespondForRegister(ActionLog actionLog) {
        super(actionLog);
    }

    @Override
    public String syncName() { return "whenSessionGrantGrantedThenWebRespondForRegister"; }

    @Override
    public SyncTrigger trigger() {
        return new SyncTrigger(SESSION_IRI, "grant", null);
    }

    @Override
    protected String whereClause() {
        return """
            ?_when_1 :concept <%s> ;
                     :name    "grant" ;
                     :userId  ?_userId ;
                     :sessionToken ?_token ;
                     :flow    ?_flow .
            << ?_when_1 :outcome "Granted" >> :flow ?_flow .
            ?_web_req :concept <%s> ;
                      :name    "request" ;
                      :flow    ?_flow ;
                      :input   ?_web_inp .
            ?_web_inp :route ?_route .
            """.formatted(SESSION_IRI, WEB_IRI);
    }

    @Override
    protected String parameterizeSparql(String sparql) {
        return bindLiteral(sparql, "_route", "/api/users");
    }

    @Override
    protected String thenBindings() {
        return """
            ?_then_1 :concept <%s> ;
                     :name    "respond" ;
                     :input   [ :statusCode 201 ;
                                :token ?_token ;
                                :userId ?_userId ] .
            """.formatted(WEB_IRI);
    }
}
