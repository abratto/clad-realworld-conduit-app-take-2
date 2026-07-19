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
        step = 2,
        triggeredBy = "User/register[refused] (blank field)",
        fires = "Web/respond[422]")
@Singleton
public final class WhenUserRegisterBlankFieldsThenWebRespondForRegister extends SyncAgent {

    private static final String WEB_IRI = FlowManager.WEB_CONCEPT_IRI;
    private static final String USER_IRI = UserConcept.IRI;

    @Inject
    public WhenUserRegisterBlankFieldsThenWebRespondForRegister(ActionLog actionLog) {
        super(actionLog);
    }

    @Override
    public String syncName() { return "whenUserRegisterBlankFieldsThenWebRespondForRegister"; }

    @Override
    public SyncTrigger trigger() {
        return new SyncTrigger(USER_IRI, "register", null);
    }

    @Override
    protected String whereClause() {
        return """
            ?_when_1 :concept <%s> ;
                     :name    "register" ;
                     :flow    ?_flow ;
                     :refusalReason ?_reason .
            << ?_when_1 :outcome "refused" >> :flow ?_flow .
            FILTER(CONTAINS(?_reason, "blank"))
            """.formatted(USER_IRI);
    }

    @Override
    protected String thenBindings() {
        return """
            ?_then_1 :concept <%s> ;
                     :name    "respond" ;
                     :input   [ :statusCode 422 ;
                                :message "can't be blank" ] .
            """.formatted(WEB_IRI);
    }
}
