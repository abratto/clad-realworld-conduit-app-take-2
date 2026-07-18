package com.conduit.app.syncs;

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
        step = 1,
        triggeredBy = "Web/request[route=login]",
        fires = "User/lookupByEmail",
        where = "route=login")
@Singleton
public final class WhenWebHandleRoutedThenUserLookupByUsernameForLogin extends SyncAgent {

    private static final String WEB_IRI = FlowManager.WEB_CONCEPT_IRI;
    private static final String USER_IRI = UserConcept.IRI;
    private static final String LOGIN_ROUTE = "login";

    @Inject
    public WhenWebHandleRoutedThenUserLookupByUsernameForLogin(ActionLog actionLog) {
        super(actionLog);
    }

    @Override
    public String syncName() { return "whenWebHandleRoutedThenUserLookupByUsernameForLogin"; }

    @Override
    public SyncTrigger trigger() { return new SyncTrigger(WEB_IRI, "request", null); }

    @Override
    protected String whereClause() {
        return """
            ?_when_1 :concept <%s> ;
                     :name    "request" ;
                     :input   ?_web_inp ;
                     :flow    ?_flow .
            ?_web_inp :route  ?_route ;
                      :email  ?_email .
            FILTER (STR(?email) != "")
            """.formatted(WEB_IRI);
    }

    @Override
    protected String thenBindings() {
        return """
            ?_then_1 :concept <%s> ;
                     :name    "lookupByEmail" ;
                     :input   [ :email ?_email ] .
            """.formatted(USER_IRI);
    }

    @Override
    protected String parameterizeSparql(String sparql) {
        return bindLiteral(sparql, "_route", LOGIN_ROUTE);
    }
}
