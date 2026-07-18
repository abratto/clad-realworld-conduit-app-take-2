package com.conduit.app.syncs;

import com.conduit.app.concepts.session.SessionConcept;
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
        fires = "Session/grant")
@Singleton
public final class WhenUserRegisterRegisteredThenSessionGrantForRegister extends SyncAgent {

    private static final String USER_IRI = UserConcept.IRI;
    private static final String SESSION_IRI = SessionConcept.IRI;

    @Inject
    public WhenUserRegisterRegisteredThenSessionGrantForRegister(ActionLog actionLog) {
        super(actionLog);
    }

    @Override
    public String syncName() { return "whenUserRegisterRegisteredThenSessionGrantForRegister"; }

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
            """.formatted(USER_IRI);
    }

    @Override
    protected String thenBindings() {
        return """
            ?_then_1 :concept <%s> ;
                     :name    "grant" ;
                     :input   [ :userId ?_userId ] .
            """.formatted(SESSION_IRI);
    }
}
