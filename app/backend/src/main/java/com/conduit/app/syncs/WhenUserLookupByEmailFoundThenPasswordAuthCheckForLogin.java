package com.conduit.app.syncs;

import com.conduit.app.concepts.passwordauth.PasswordAuthConcept;
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
        step = 2,
        triggeredBy = "User/lookupByEmail[FOUND]",
        fires = "PasswordAuth/check")
@Singleton
public final class WhenUserLookupByEmailFoundThenPasswordAuthCheckForLogin extends SyncAgent {

    private static final String USER_IRI = UserConcept.IRI;
    private static final String PW_IRI = PasswordAuthConcept.IRI;

    @Inject
    public WhenUserLookupByEmailFoundThenPasswordAuthCheckForLogin(ActionLog actionLog) {
        super(actionLog);
    }

    @Override
    public String syncName() { return "whenUserLookupByEmailFoundThenPasswordAuthCheckForLogin"; }

    @Override
    public SyncTrigger trigger() {
        return new SyncTrigger(USER_IRI, "lookupByEmail", null);
    }

    @Override
    protected String whereClause() {
        return """
            ?_when_1 :concept <%s> ;
                     :name    "lookupByEmail" ;
                     :flow    ?_flow ;
                     :userId  ?_userId .
            << ?_when_1 :outcome "FOUND" >> :flow ?_flow .
            ?_web_req :concept <%s> ;
                      :name    "request" ;
                      :flow    ?_flow ;
                      :input   ?_inp .
            ?_inp :password ?_password .
            """.formatted(USER_IRI, FlowManager.WEB_CONCEPT_IRI);
    }

    @Override
    protected String thenBindings() {
        return """
            ?_then_1 :concept <%s> ;
                     :name    "check" ;
                     :input   [ :userId ?_userId ; :password ?_password ] .
            """.formatted(PW_IRI);
    }
}
