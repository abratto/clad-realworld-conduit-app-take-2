package com.conduit.app.syncs;

import com.conduit.app.engine.ActionLog;
import com.conduit.app.engine.FlowManager;
import com.conduit.app.engine.SyncAgent;
import com.conduit.app.engine.SyncMetadata;
import com.conduit.app.engine.SyncTrigger;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@SyncMetadata(
        flow = "Register",
        step = 2,
        triggeredBy = "Web/handle[Refused]",
        fires = "Web/respond[422]")
@Singleton
public final class WhenWebHandleBlankFieldsThenWebRespondForRegister extends SyncAgent {

    private static final String WEB_IRI = FlowManager.WEB_CONCEPT_IRI;

    @Inject
    public WhenWebHandleBlankFieldsThenWebRespondForRegister(ActionLog actionLog) {
        super(actionLog);
    }

    @Override
    public String syncName() { return "whenWebHandleBlankFieldsThenWebRespondForRegister"; }

    @Override
    public SyncTrigger trigger() {
        return new SyncTrigger(WEB_IRI, "handle", null);
    }

    @Override
    protected String whereClause() {
        return """
            ?_when_1 :concept <%s> ;
                     :name    "handle" ;
                     :flow    ?_flow .
            << ?_when_1 :outcome "refused" >> :flow ?_flow .
            ?_req :concept <%s> ;
                  :name    "request" ;
                  :flow    ?_flow ;
                  :input   ?_req_inp .
            ?_req_inp :route ?_route .
            """.formatted(WEB_IRI, WEB_IRI);
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
                     :input   [ :statusCode 422 ;
                                :bodyType "error" ] .
            """.formatted(WEB_IRI);
    }
}
