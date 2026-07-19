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

@Singleton
public final class WhenUserLookupByUsernameFoundThenSessionLookupForViewProfile extends SyncAgent {
    private static final String WEB_IRI = FlowManager.WEB_CONCEPT_IRI;
    @Inject public WhenUserLookupByUsernameFoundThenSessionLookupForViewProfile(ActionLog l) { super(l); }
    @Override public String syncName() { return "whenUserLookupByUsernameFoundThenSessionLookupForViewProfile"; }
    @Override public SyncTrigger trigger() { return new SyncTrigger(UserConcept.IRI, "lookupByUsername", null); }
    @Override protected String whereClause() {
        return """
            ?_when_1 :concept <%s> ; :name "lookupByUsername" ; :flow ?_flow ; :userId ?_userId .
            << ?_when_1 :outcome "FOUND" >> :flow ?_flow .
            ?_req :concept <%s> ; :name "request" ; :flow ?_flow ; :input ?_inp .
            ?_inp :token ?_token .
            FILTER (STR(?token) != "")
            """.formatted(UserConcept.IRI, WEB_IRI);
    }
    @Override protected String thenBindings() {
        return "?_then_1 :concept <%s> ; :name \"lookup\" ; :input [ :token ?_token ] .".formatted(SessionConcept.IRI);
    }
}
