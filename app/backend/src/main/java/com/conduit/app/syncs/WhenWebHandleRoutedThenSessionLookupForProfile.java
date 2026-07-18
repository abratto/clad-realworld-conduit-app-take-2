package com.conduit.app.syncs;

import com.conduit.app.concepts.session.SessionConcept;
import com.conduit.app.engine.ActionLog;
import com.conduit.app.engine.FlowManager;
import com.conduit.app.engine.SyncAgent;
import com.conduit.app.engine.SyncMetadata;
import com.conduit.app.engine.SyncTrigger;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@SyncMetadata(flow = "Profile", step = 3, triggeredBy = "Web/handle[Routed]", fires = "Session/lookup")
@Singleton
public final class WhenWebHandleRoutedThenSessionLookupForProfile extends SyncAgent {
    private static final String WEB_IRI = FlowManager.WEB_CONCEPT_IRI;
    @Inject public WhenWebHandleRoutedThenSessionLookupForProfile(ActionLog l) { super(l); }
    @Override public String syncName() { return "whenWebHandleRoutedThenSessionLookupForProfile"; }
    @Override public SyncTrigger trigger() { return new SyncTrigger(WEB_IRI, "handle", null); }
    @Override protected String whereClause() {
        return "?_when_1 :concept <%s> ; :name \"handle\" ; :input ?_inp ; :flow ?_flow .\n?_inp :token ?_token .".formatted(WEB_IRI);
    }
    @Override protected String thenBindings() {
        return "?_then_1 :concept <%s> ; :name \"lookup\" ; :input [ :token ?_token ] .".formatted(SessionConcept.IRI);
    }
}
