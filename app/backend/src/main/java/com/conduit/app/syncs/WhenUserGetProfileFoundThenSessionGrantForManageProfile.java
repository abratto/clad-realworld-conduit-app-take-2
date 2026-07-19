package com.conduit.app.syncs;

import com.conduit.app.concepts.session.SessionConcept;
import com.conduit.app.concepts.user.UserConcept;
import com.conduit.app.engine.ActionLog;
import com.conduit.app.engine.SyncAgent;
import com.conduit.app.engine.SyncMetadata;
import com.conduit.app.engine.SyncTrigger;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@SyncMetadata(flow = "Profile", step = 5, triggeredBy = "User/getProfile[FOUND]", fires = "Session/grant")
@Singleton
public final class WhenUserGetProfileFoundThenSessionGrantForManageProfile extends SyncAgent {
    @Inject public WhenUserGetProfileFoundThenSessionGrantForManageProfile(ActionLog l) { super(l); }
    @Override public String syncName() { return "whenUserGetProfileFoundThenSessionGrantForManageProfile"; }
    @Override public SyncTrigger trigger() { return new SyncTrigger(UserConcept.IRI, "getProfile", null); }
    @Override protected String whereClause() {
        return "?_when_1 :concept <%s> ; :name \"getProfile\" ; :flow ?_flow ; :userId ?_userId .\n<< ?_when_1 :outcome \"FOUND\" >> :flow ?_flow .".formatted(UserConcept.IRI);
    }
    @Override protected String thenBindings() {
        return "?_then_1 :concept <%s> ; :name \"grant\" ; :input [ :userId ?_userId ] .".formatted(SessionConcept.IRI);
    }
}
