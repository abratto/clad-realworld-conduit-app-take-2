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
        triggeredBy = "Web/handle[Routed]",
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
        return new SyncTrigger(WEB_IRI, "handle", null);
    }

    @Override
    protected String whereClause() {
        return """
            ?_when_1 :concept <%s> ;
                     :name    "handle" ;
                     :input   ?_handle_inp ;
                     :flow    ?_flow .
            ?_handle_inp :username ?_username ;
                         :email    ?_email ;
                         :password ?_password .
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
                     :name    "register" ;
                     :input   [ :username ?_username ;
                                :email    ?_email ;
                                :password ?_password ] .
            """.formatted(USER_IRI);
    }
}
