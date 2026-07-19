package com.conduit.app.syncs;

import com.conduit.app.engine.ActionLog;
import com.conduit.app.engine.FlowManager;
import com.conduit.app.engine.SyncAgent;
import com.conduit.app.engine.SyncMetadata;
import com.conduit.app.engine.SyncTrigger;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public final class WhenFollowIsFollowingTrueThenWebRespondForViewProfile extends SyncAgent {
    private static final String WEB_IRI = FlowManager.WEB_CONCEPT_IRI;
    private static final String FOLLOW_IRI = "https://clad.dev/concept/follow";
    @Inject public WhenFollowIsFollowingTrueThenWebRespondForViewProfile(ActionLog l) { super(l); }
    @Override public String syncName() { return "whenFollowIsFollowingTrueThenWebRespondForViewProfile"; }
    @Override public SyncTrigger trigger() { return new SyncTrigger(FOLLOW_IRI, "isFollowing", null); }
    @Override protected String whereClause() {
        return "?_when_1 :concept <%s> ; :name \"isFollowing\" ; :flow ?_flow .\n<< ?_when_1 :outcome \"true\" >> :flow ?_flow .".formatted(FOLLOW_IRI);
    }
    @Override protected String thenBindings() {
        return "?_then_1 :concept <%s> ; :name \"respond\" ; :input [ :statusCode 200 ; :following \"true\" ] .".formatted(WEB_IRI);
    }
}
