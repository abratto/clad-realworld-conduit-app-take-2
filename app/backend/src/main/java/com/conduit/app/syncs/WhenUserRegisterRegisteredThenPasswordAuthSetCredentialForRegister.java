package com.conduit.app.syncs;

import com.conduit.app.concepts.passwordauth.PasswordAuthConcept;
import com.conduit.app.concepts.user.UserConcept;
import com.conduit.app.engine.ActionLog;
import com.conduit.app.engine.SyncAgent;
import com.conduit.app.engine.SyncMetadata;
import com.conduit.app.engine.SyncTrigger;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@SyncMetadata(
        flow = "Register",
        step = 4,
        triggeredBy = "User/register[Registered]",
        fires = "PasswordAuth/setCredential")
@Singleton
public final class WhenUserRegisterRegisteredThenPasswordAuthSetCredentialForRegister extends SyncAgent {

    private static final String USER_IRI = UserConcept.IRI;
    private static final String PW_IRI = PasswordAuthConcept.IRI;

    @Inject
    public WhenUserRegisterRegisteredThenPasswordAuthSetCredentialForRegister(ActionLog actionLog) {
        super(actionLog);
    }

    @Override
    public String syncName() { return "whenUserRegisterRegisteredThenPasswordAuthSetCredentialForRegister"; }

    @Override
    public SyncTrigger trigger() {
        return new SyncTrigger(USER_IRI, "register", null);
    }

    @Override
    protected String whereClause() {
        return """
            ?_when_1 :concept <%s> ;
                     :name    "register" ;
                     :userId  ?_userId ;
                     :flow    ?_flow .
            << ?_when_1 :outcome "Registered" >> :flow ?_flow .
            ?_web_req :concept <%s> ;
                      :name    "request" ;
                      :flow    ?_flow ;
                      :input   ?_inp .
            ?_inp :password ?_password .
            """.formatted(USER_IRI, "https://clad.dev/concept/web");
    }

    @Override
    protected String thenBindings() {
        return """
            ?_then_1 :concept <%s> ;
                     :name    "setCredential" ;
                     :input   [ :userId ?_userId ; :password ?_password ] .
            """.formatted(PW_IRI);
    }
}
