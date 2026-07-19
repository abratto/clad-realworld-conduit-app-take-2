package com.conduit.app.syncs;
import com.conduit.app.concepts.session.SessionConcept;
import com.conduit.app.concepts.user.UserConcept;
import com.conduit.app.engine.*;
import jakarta.inject.*;
@Singleton
public final class WhenSessionLookupFoundThenUserLookupByUsernameForFollowUser extends SyncAgent {
    @Inject public WhenSessionLookupFoundThenUserLookupByUsernameForFollowUser(ActionLog l) { super(l); }
    @Override public String syncName() { return "whenSessionLookupFoundThenUserLookupByUsernameForFollowUser"; }
    @Override public SyncTrigger trigger() { return new SyncTrigger(SessionConcept.IRI, "lookup", null); }
    @Override protected String whereClause() {
        return "?_when_1 :concept <%s> ; :name \"lookup\" ; :flow ?_flow .\n<< ?_when_1 :outcome \"FOUND\" >> :flow ?_flow .\n?_req :concept <%s> ; :name \"request\" ; :flow ?_flow ; :input ?_inp .\n?_inp :username ?_username .".formatted(SessionConcept.IRI, FlowManager.WEB_CONCEPT_IRI);
    }
    @Override protected String thenBindings() {
        return "?_then_1 :concept <%s> ; :name \"lookupByUsername\" ; :input [ :username ?_username ] .".formatted(UserConcept.IRI);
    }
}
