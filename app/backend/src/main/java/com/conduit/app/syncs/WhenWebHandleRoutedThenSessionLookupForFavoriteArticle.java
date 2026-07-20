package com.conduit.app.syncs;
import com.conduit.app.concepts.session.SessionConcept;
import com.conduit.app.engine.*;
import jakarta.inject.*;
@Singleton
public final class WhenWebHandleRoutedThenSessionLookupForFavoriteArticle extends SyncAgent {
    private static final String WEB_IRI = FlowManager.WEB_CONCEPT_IRI;
    private static final String ARTICLES_ROUTE = "/api/articles";
    @Inject public WhenWebHandleRoutedThenSessionLookupForFavoriteArticle(ActionLog l) { super(l); }
    @Override public String syncName() { return "whenWebHandleRoutedThenSessionLookupForFavoriteArticle"; }
    @Override public SyncTrigger trigger() { return new SyncTrigger(WEB_IRI, "handle", null); }
    @Override protected String whereClause() {
        return "?_when_1 :concept <%s> ; :name \"handle\" ; :input ?_handle_inp ; :flow ?_flow .\n?_handle_inp :token ?_token .\n?_req :concept <%s> ; :name \"request\" ; :flow ?_flow ; :input ?_req_inp .\n?_req_inp :route ?_route .".formatted(WEB_IRI, WEB_IRI);
    }
    @Override protected String thenBindings() {
        return "?_then_1 :concept <%s> ; :name \"lookup\" ; :input [ :token ?_token ] .".formatted(SessionConcept.IRI);
    }
    @Override protected String parameterizeSparql(String sparql) {
        return bindLiteral(sparql, "_route", ARTICLES_ROUTE);
    }
}
