package com.conduit.app.syncs;
import com.conduit.app.concepts.session.SessionConcept;
import com.conduit.app.engine.*;
import jakarta.inject.*;
@Singleton
public final class WhenWebHandleRoutedThenSessionLookupForFollow extends SyncAgent {
    private static final String WEB_IRI = FlowManager.WEB_CONCEPT_IRI;
    @Inject public WhenWebHandleRoutedThenSessionLookupForFollow(ActionLog l) { super(l); }
    @Override public String syncName() { return "whenWebHandleRoutedThenSessionLookupForFollow"; }
    @Override public SyncTrigger trigger() { return new SyncTrigger(WEB_IRI, "handle", null); }
    @Override protected String whereClause() {
        return "?_when_1 :concept <%s> ; :name \"handle\" ; :input ?_inp ; :flow ?_flow .\n?_inp :token ?_token .".formatted(WEB_IRI);
    }
    @Override protected String thenBindings() {
        return "?_then_1 :concept <%s> ; :name \"lookup\" ; :input [ :token ?_token ] .".formatted(SessionConcept.IRI);
    }
}
