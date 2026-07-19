package com.conduit.app.syncs;

import com.conduit.app.concepts.article.ArticleConcept;
import com.conduit.app.engine.ActionLog;
import com.conduit.app.engine.FlowManager;
import com.conduit.app.engine.SyncAgent;
import com.conduit.app.engine.SyncMetadata;
import com.conduit.app.engine.SyncTrigger;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@SyncMetadata(flow = "Article", step = 6, triggeredBy = "Article/create[refused] (blank title)", fires = "Web/respond[422]")
@Singleton
public final class WhenArticleCreateRefusedBlankTitleThenWebRespondForArticle extends SyncAgent {
    private static final String WEB_IRI = FlowManager.WEB_CONCEPT_IRI;
    private static final String ARTICLE_ROUTE = "/api/articles";
    @Inject public WhenArticleCreateRefusedBlankTitleThenWebRespondForArticle(ActionLog l) { super(l); }
    @Override public String syncName() { return "whenArticleCreateRefusedBlankTitleThenWebRespondForArticle"; }
    @Override public SyncTrigger trigger() { return new SyncTrigger(ArticleConcept.IRI, "create", null); }
    @Override protected String whereClause() {
        return "?_when_1 :concept <%s> ; :name \"create\" ; :flow ?_flow ; :refusalReason ?_reason .\n<< ?_when_1 :outcome \"refused\" >> :flow ?_flow .\nFILTER(CONTAINS(?_reason, \"blank\"))\n?_req :concept <%s> ; :name \"request\" ; :flow ?_flow ; :input ?_inp .\n?_inp :route ?_route .".formatted(ArticleConcept.IRI, WEB_IRI);
    }
    @Override protected String thenBindings() {
        return "?_then_1 :concept <%s> ; :name \"respond\" ; :input [ :statusCode 422 ; :message \"can't be blank\" ] .".formatted(WEB_IRI);
    }
    @Override protected String parameterizeSparql(String sparql) {
        return bindLiteral(sparql, "_route", ARTICLE_ROUTE);
    }
}
