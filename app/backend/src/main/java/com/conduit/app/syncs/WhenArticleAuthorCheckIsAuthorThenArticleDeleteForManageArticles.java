package com.conduit.app.syncs;

import com.conduit.app.concepts.article.ArticleConcept;
import com.conduit.app.engine.ActionLog;
import com.conduit.app.engine.FlowManager;
import com.conduit.app.engine.SyncAgent;
import com.conduit.app.engine.SyncMetadata;
import com.conduit.app.engine.SyncTrigger;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@SyncMetadata(flow = "Article", step = 8, triggeredBy = "Article/authorCheck[IsAuthor]", fires = "Article/delete")
@Singleton
public final class WhenArticleAuthorCheckIsAuthorThenArticleDeleteForManageArticles extends SyncAgent {
    private static final String WEB_IRI = FlowManager.WEB_CONCEPT_IRI;
    @Inject public WhenArticleAuthorCheckIsAuthorThenArticleDeleteForManageArticles(ActionLog l) { super(l); }
    @Override public String syncName() { return "whenArticleAuthorCheckIsAuthorThenArticleDeleteForManageArticles"; }
    @Override public SyncTrigger trigger() { return new SyncTrigger(ArticleConcept.IRI, "authorCheck", null); }
    @Override protected String whereClause() {
        return "?_when_1 :concept <%s> ; :name \"authorCheck\" ; :flow ?_flow ; :articleId ?_articleId ; :memberId ?_memberId .\n<< ?_when_1 :outcome \"IsAuthor\" >> :flow ?_flow .\n?_req :concept <%s> ; :name \"request\" ; :flow ?_flow ; :input ?_inp .\n?_inp :slug ?_slug .\nFILTER NOT EXISTS { ?_inp :title ?_title }".formatted(ArticleConcept.IRI, WEB_IRI);
    }
    @Override protected String thenBindings() {
        return "?_then_1 :concept <%s> ; :name \"delete\" ; :input [ :slug ?_slug ] .".formatted(ArticleConcept.IRI);
    }
}
