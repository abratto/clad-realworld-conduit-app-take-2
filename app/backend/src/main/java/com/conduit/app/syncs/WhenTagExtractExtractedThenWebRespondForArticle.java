package com.conduit.app.syncs;

import com.conduit.app.engine.ActionLog;
import com.conduit.app.engine.FlowManager;
import com.conduit.app.engine.SyncAgent;
import com.conduit.app.engine.SyncMetadata;
import com.conduit.app.engine.SyncTrigger;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@SyncMetadata(flow = "Article", step = 7, triggeredBy = "Tag/extract[Extracted]", fires = "Web/respond[201]")
@Singleton
public final class WhenTagExtractExtractedThenWebRespondForArticle extends SyncAgent {
    private static final String WEB_IRI = FlowManager.WEB_CONCEPT_IRI;
    private static final String TAG_IRI = "https://clad.dev/concept/tag";
    private static final String ARTICLE_ROUTE = "/api/articles";
    @Inject public WhenTagExtractExtractedThenWebRespondForArticle(ActionLog l) { super(l); }
    @Override public String syncName() { return "whenTagExtractExtractedThenWebRespondForArticle"; }
    @Override public SyncTrigger trigger() { return new SyncTrigger(TAG_IRI, "extract", null); }
    @Override protected String whereClause() {
        return "?_when_1 :concept <%s> ; :name \"extract\" ; :flow ?_flow .\n<< ?_when_1 :outcome \"Extracted\" >> :flow ?_flow .\n?_req :concept <%s> ; :name \"request\" ; :flow ?_flow ; :input ?_inp .\n?_inp :route ?_route .".formatted(TAG_IRI, WEB_IRI);
    }
    @Override protected String thenBindings() {
        return "?_then_1 :concept <%s> ; :name \"respond\" ; :input [ :statusCode 201 ; :message \"created\" ] .".formatted(WEB_IRI);
    }
    @Override protected String parameterizeSparql(String sparql) {
        return bindLiteral(sparql, "_route", ARTICLE_ROUTE);
    }
}
