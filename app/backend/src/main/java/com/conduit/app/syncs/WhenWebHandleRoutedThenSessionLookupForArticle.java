package com.conduit.app.syncs;

import com.conduit.app.concepts.session.SessionConcept;
import com.conduit.app.engine.ActionLog;
import com.conduit.app.engine.FlowManager;
import com.conduit.app.engine.SyncAgent;
import com.conduit.app.engine.SyncMetadata;
import com.conduit.app.engine.SyncTrigger;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@SyncMetadata(flow = "Article", step = 3, triggeredBy = "Web/handle[Routed]", fires = "Session/lookup")
@Singleton
public final class WhenWebHandleRoutedThenSessionLookupForArticle extends SyncAgent {
    private static final String WEB_IRI = FlowManager.WEB_CONCEPT_IRI;
    private static final String ARTICLE_ROUTE = "/api/articles";
    @Inject public WhenWebHandleRoutedThenSessionLookupForArticle(ActionLog l) { super(l); }
    @Override public String syncName() { return "whenWebHandleRoutedThenSessionLookupForArticle"; }
    @Override public SyncTrigger trigger() { return new SyncTrigger(WEB_IRI, "handle", null); }
    @Override protected String whereClause() {
        return "?_when_1 :concept <%s> ; :name \"handle\" ; :input ?_handle_inp ; :flow ?_flow .\n?_handle_inp :token ?_token .\n?_req :concept <%s> ; :name \"request\" ; :flow ?_flow ; :input ?_req_inp .\n?_req_inp :route ?_route .".formatted(WEB_IRI, WEB_IRI);
    }
    @Override protected String thenBindings() {
        return "?_then_1 :concept <%s> ; :name \"lookup\" ; :input [ :token ?_token ] .".formatted(SessionConcept.IRI);
    }
    @Override protected String parameterizeSparql(String sparql) {
        return bindLiteral(sparql, "_route", ARTICLE_ROUTE);
    }
}
