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

@SyncMetadata(
        flow = "Login",
        step = 4,
        triggeredBy = "Session/grant[Granted]",
        fires = "Web/respond[200]")
@Singleton
public final class WhenSessionGrantGrantedThenWebRespondForLogin extends SyncAgent {

    private static final String WEB_IRI = FlowManager.WEB_CONCEPT_IRI;
    private static final String SESSION_IRI = SessionConcept.IRI;
    private static final String LOGIN_ROUTE = "login";

    @Inject
    public WhenSessionGrantGrantedThenWebRespondForLogin(ActionLog actionLog) {
        super(actionLog);
    }

    @Override
    public String syncName() { return "whenSessionGrantGrantedThenWebRespondForLogin"; }

    @Override
    public SyncTrigger trigger() {
        return new SyncTrigger(SESSION_IRI, "grant", null);
    }

    @Override
    protected String whereClause() {
        return """
            ?_when_1 :concept <%s> ;
                     :name    "grant" ;
                     :sessionToken ?_token .
            << ?_when_1 :outcome "Granted" >> :flow ?_flow .
            ?_web_req :concept <%s> ;
                      :name    "request" ;
                      :flow    ?_flow ;
                      :input   ?_web_inp .
            ?_web_inp :route ?_route .
            OPTIONAL { ?_web_inp :email ?_email . }
            OPTIONAL { ?_web_inp :username ?_username_from_req . }
            OPTIONAL { ?_lookup_action :concept <%s> ; :name "lookupByEmail" ; :flow ?_flow ; :username ?_username . << ?_lookup_action :outcome "FOUND" >> :flow ?_flow . }
            OPTIONAL { ?_lookup_user :concept <%s> ; :name "lookupByUsername" ; :flow ?_flow ; :username ?_username . << ?_lookup_user :outcome "FOUND" >> :flow ?_flow . }
            BIND(COALESCE(?_username, ?_username_from_req, "") AS ?_un)
            """.formatted(SESSION_IRI, WEB_IRI, UserConcept.IRI, UserConcept.IRI);
    }

    @Override
    protected String thenBindings() {
        return """
            ?_then_1 :concept <%s> ;
                     :name    "respond" ;
                     :input   [ :statusCode 200 ; :token ?_token ; :sessionToken ?_token ; :email ?_email ; :username ?_un ] .
            """.formatted(WEB_IRI);
    }

    @Override
    protected String parameterizeSparql(String sparql) {
        return bindLiteral(sparql, "_route", LOGIN_ROUTE);
    }
}
