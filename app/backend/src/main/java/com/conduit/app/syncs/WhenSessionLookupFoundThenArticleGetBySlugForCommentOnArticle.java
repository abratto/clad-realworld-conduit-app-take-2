package com.conduit.app.syncs;

import com.conduit.app.concepts.article.ArticleConcept;
import com.conduit.app.concepts.session.SessionConcept;
import com.conduit.app.engine.ActionLog;
import com.conduit.app.engine.FlowManager;
import com.conduit.app.engine.SyncAgent;
import com.conduit.app.engine.SyncMetadata;
import com.conduit.app.engine.SyncTrigger;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@SyncMetadata(flow = "Comment", step = 5, triggeredBy = "Session/lookup[FOUND]", fires = "Article/getBySlug")
@Singleton
public final class WhenSessionLookupFoundThenArticleGetBySlugForCommentOnArticle extends SyncAgent {
    private static final String WEB_IRI = FlowManager.WEB_CONCEPT_IRI;
    private static final String ARTICLE_IRI = ArticleConcept.IRI;
    private static final String COMMENT_ROUTE = "/api/articles";
    @Inject public WhenSessionLookupFoundThenArticleGetBySlugForCommentOnArticle(ActionLog l) { super(l); }
    @Override public String syncName() { return "whenSessionLookupFoundThenArticleGetBySlugForCommentOnArticle"; }
    @Override public SyncTrigger trigger() { return new SyncTrigger(SessionConcept.IRI, "lookup", null); }
    @Override protected String whereClause() {
        return "?_when_1 :concept <%s> ; :name \"lookup\" ; :flow ?_flow ; :userId ?_userId .\n<< ?_when_1 :outcome \"FOUND\" >> :flow ?_flow .\n?_req :concept <%s> ; :name \"request\" ; :flow ?_flow ; :input ?_inp .\n?_inp :route ?_route ; :slug ?_slug .".formatted(SessionConcept.IRI, WEB_IRI);
    }
    @Override protected String thenBindings() {
        return "?_then_1 :concept <%s> ; :name \"getBySlug\" ; :input [ :slug ?_slug ] .".formatted(ARTICLE_IRI);
    }
    @Override protected String parameterizeSparql(String sparql) {
        return bindLiteral(sparql, "_route", COMMENT_ROUTE);
    }
}
