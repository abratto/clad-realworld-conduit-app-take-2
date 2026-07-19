package com.conduit.app.syncs;

import com.conduit.app.concepts.article.ArticleConcept;
import com.conduit.app.concepts.session.SessionConcept;
import com.conduit.app.engine.ActionLog;
import com.conduit.app.engine.SyncAgent;
import com.conduit.app.engine.SyncMetadata;
import com.conduit.app.engine.SyncTrigger;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@SyncMetadata(flow = "Article", step = 7, triggeredBy = "Article/getBySlug[FOUND]", fires = "Article/authorCheck")
@Singleton
public final class WhenArticleGetBySlugFoundThenArticleAuthorCheckForManageArticles extends SyncAgent {
    @Inject public WhenArticleGetBySlugFoundThenArticleAuthorCheckForManageArticles(ActionLog l) { super(l); }
    @Override public String syncName() { return "whenArticleGetBySlugFoundThenArticleAuthorCheckForManageArticles"; }
    @Override public SyncTrigger trigger() { return new SyncTrigger(ArticleConcept.IRI, "getBySlug", null); }
    @Override protected String whereClause() {
        return "?_when_1 :concept <%s> ; :name \"getBySlug\" ; :flow ?_flow ; :articleId ?_articleId .\n<< ?_when_1 :outcome \"FOUND\" >> :flow ?_flow .\n?_session :concept <%s> ; :name \"lookup\" ; :flow ?_flow ; :userId ?_userId .\n<< ?_session :outcome \"FOUND\" >> :flow ?_flow .".formatted(ArticleConcept.IRI, SessionConcept.IRI);
    }
    @Override protected String thenBindings() {
        return "?_then_1 :concept <%s> ; :name \"authorCheck\" ; :input [ :articleId ?_articleId ; :memberId ?_userId ] .".formatted(ArticleConcept.IRI);
    }
}
