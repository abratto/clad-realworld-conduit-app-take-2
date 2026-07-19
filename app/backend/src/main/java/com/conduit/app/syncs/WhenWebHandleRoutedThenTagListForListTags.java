package com.conduit.app.syncs;
import com.conduit.app.engine.*;
import jakarta.inject.*;
@Singleton
public final class WhenWebHandleRoutedThenTagListForListTags extends SyncAgent {
    private static final String WEB_IRI = FlowManager.WEB_CONCEPT_IRI;
    @Inject public WhenWebHandleRoutedThenTagListForListTags(ActionLog l) { super(l); }
    @Override public String syncName() { return "whenWebHandleRoutedThenTagListForListTags"; }
    @Override public SyncTrigger trigger() { return new SyncTrigger(WEB_IRI, "handle", null); }
    @Override protected String whereClause() {
        return "?_when_1 :concept <%s> ; :name \"handle\" ; :flow ?_flow .".formatted(WEB_IRI);
    }
    @Override protected String thenBindings() {
        return "?_then_1 :concept <https://clad.dev/concept/tag> ; :name \"list\" ; :input [] .";
    }
}
