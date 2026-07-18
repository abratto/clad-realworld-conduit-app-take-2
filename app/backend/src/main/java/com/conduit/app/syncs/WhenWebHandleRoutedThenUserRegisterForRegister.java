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
        flow = "Register",
        step = 3,
        triggeredBy = "Web/request[route=/api/users]",
        fires = "User/register")
@Singleton
public final class WhenWebHandleRoutedThenUserRegisterForRegister extends SyncAgent {

    private static final String WEB_IRI = FlowManager.WEB_CONCEPT_IRI;
    private static final String USER_IRI = UserConcept.IRI;

    @Inject
    public WhenWebHandleRoutedThenUserRegisterForRegister(ActionLog actionLog) {
        super(actionLog);
    }

    @Override
    public String syncName() { return "whenWebHandleRoutedThenUserRegisterForRegister"; }

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
            ?_inp :route    ?_route ;
                  :username ?_username ;
                  :email    ?_email ;
                  :password ?_password .
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
                     :name    "register" ;
                     :input   [ :username ?_username ;
                                :email    ?_email ;
                                :password ?_password ] .
            """.formatted(USER_IRI);
    }
}
