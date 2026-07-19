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
        triggeredBy = "Web/request[route=/api/users, refused]",
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
        return new SyncTrigger(WEB_IRI, "request", null);
    }

    @Override
    protected String whereClause() {
        return """
            ?_when_1 :concept <%s> ;
                     :name    "request" ;
                     :input   ?_inp ;
                     :flow    ?_flow .
            ?_inp :route ?_route .
            << ?_when_1 :outcome "refused" >> :flow ?_flow .
            """.formatted(WEB_IRI);
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
