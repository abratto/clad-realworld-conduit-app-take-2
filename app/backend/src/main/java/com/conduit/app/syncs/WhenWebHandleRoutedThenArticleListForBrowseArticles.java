package com.conduit.app.syncs;
import com.conduit.app.concepts.article.ArticleConcept;
import com.conduit.app.engine.*;
import jakarta.inject.*;
@Singleton
public final class WhenWebHandleRoutedThenArticleListForBrowseArticles extends SyncAgent {
    private static final String WEB_IRI = FlowManager.WEB_CONCEPT_IRI;
    @Inject public WhenWebHandleRoutedThenArticleListForBrowseArticles(ActionLog l) { super(l); }
    @Override public String syncName() { return "whenWebHandleRoutedThenArticleListForBrowseArticles"; }
    @Override public SyncTrigger trigger() { return new SyncTrigger(WEB_IRI, "handle", null); }
    @Override protected String whereClause() {
        return "?_when_1 :concept <%s> ; :name \"handle\" ; :input ?_inp ; :flow ?_flow .\n?_inp :limit ?_limit ; :offset ?_offset .".formatted(WEB_IRI);
    }
    @Override protected String thenBindings() {
        return "?_then_1 :concept <%s> ; :name \"list\" ; :input [ :limit ?_limit ; :offset ?_offset ] .".formatted(ArticleConcept.IRI);
    }
}
