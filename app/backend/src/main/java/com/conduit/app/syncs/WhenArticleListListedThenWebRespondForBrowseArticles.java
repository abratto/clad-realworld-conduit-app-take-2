package com.conduit.app.syncs;
import com.conduit.app.engine.*;
import jakarta.inject.*;
@Singleton
public final class WhenArticleListListedThenWebRespondForBrowseArticles extends SyncAgent {
    private static final String WEB_IRI = FlowManager.WEB_CONCEPT_IRI;
    private static final String ARTICLE_IRI = "https://clad.dev/concept/article";
    @Inject public WhenArticleListListedThenWebRespondForBrowseArticles(ActionLog l) { super(l); }
    @Override public String syncName() { return "whenArticleListListedThenWebRespondForBrowseArticles"; }
    @Override public SyncTrigger trigger() { return new SyncTrigger(ARTICLE_IRI, "list", null); }
    @Override protected String whereClause() {
        return "?_when_1 :concept <%s> ; :name \"list\" ; :flow ?_flow ; :count ?_count .\n<< ?_when_1 :outcome \"Listed\" >> :flow ?_flow .".formatted(ARTICLE_IRI);
    }
    @Override protected String thenBindings() {
        return "?_then_1 :concept <%s> ; :name \"respond\" ; :input [ :statusCode 200 ; :count ?_count ] .".formatted(WEB_IRI);
    }
}
