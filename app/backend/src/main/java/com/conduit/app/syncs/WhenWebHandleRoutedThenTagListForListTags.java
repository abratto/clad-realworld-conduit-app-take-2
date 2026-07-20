package com.conduit.app.syncs;
import com.conduit.app.engine.*;
import jakarta.inject.*;
@Singleton
public final class WhenWebHandleRoutedThenTagListForListTags extends SyncAgent {
    private static final String WEB_IRI = FlowManager.WEB_CONCEPT_IRI;
    private static final String TAGS_ROUTE = "/api/tags";
    @Inject public WhenWebHandleRoutedThenTagListForListTags(ActionLog l) { super(l); }
    @Override public String syncName() { return "whenWebHandleRoutedThenTagListForListTags"; }
    @Override public SyncTrigger trigger() { return new SyncTrigger(WEB_IRI, "handle", null); }
    @Override protected String whereClause() {
        return "?_when_1 :concept <%s> ; :name \"handle\" ; :flow ?_flow .\n?_req :concept <%s> ; :name \"request\" ; :flow ?_flow ; :input ?_inp .\n?_inp :route ?_route .".formatted(WEB_IRI, WEB_IRI);
    }
    @Override protected String thenBindings() {
        return "?_then_1 :concept <https://clad.dev/concept/tag> ; :name \"list\" ; :input [] .";
    }
    @Override protected String parameterizeSparql(String sparql) {
        return bindLiteral(sparql, "_route", TAGS_ROUTE);
    }
}
