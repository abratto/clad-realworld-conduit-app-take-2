package com.conduit.app.syncs;

import com.conduit.app.concepts.article.ArticleConcept;
import com.conduit.app.engine.ActionLog;
import com.conduit.app.engine.SyncAgent;
import com.conduit.app.engine.SyncMetadata;
import com.conduit.app.engine.SyncTrigger;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@SyncMetadata(flow = "Article", step = 6, triggeredBy = "Article/create[Created]", fires = "Tag/extract")
@Singleton
public final class WhenArticleCreateCreatedThenTagExtractForArticle extends SyncAgent {
    private static final String TAG_IRI = "https://clad.dev/concept/tag";
    @Inject public WhenArticleCreateCreatedThenTagExtractForArticle(ActionLog l) { super(l); }
    @Override public String syncName() { return "whenArticleCreateCreatedThenTagExtractForArticle"; }
    @Override public SyncTrigger trigger() { return new SyncTrigger(ArticleConcept.IRI, "create", null); }
    @Override protected String whereClause() {
        return "?_when_1 :concept <%s> ; :name \"create\" ; :flow ?_flow ; :articleId ?_articleId .\n<< ?_when_1 :outcome \"Created\" >> :flow ?_flow .".formatted(ArticleConcept.IRI);
    }
    @Override protected String thenBindings() {
        return "?_then_1 :concept <%s> ; :name \"extract\" ; :input [ :articleId ?_articleId ] .".formatted(TAG_IRI);
    }
}
