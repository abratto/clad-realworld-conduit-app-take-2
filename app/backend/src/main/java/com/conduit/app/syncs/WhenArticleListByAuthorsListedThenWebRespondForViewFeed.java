package com.conduit.app.syncs;
import com.conduit.app.concepts.article.ArticleConcept;
import com.conduit.app.engine.*;
import jakarta.inject.*;
@Singleton
public final class WhenArticleListByAuthorsListedThenWebRespondForViewFeed extends SyncAgent {
    private static final String WEB_IRI = FlowManager.WEB_CONCEPT_IRI;
    @Inject public WhenArticleListByAuthorsListedThenWebRespondForViewFeed(ActionLog l) { super(l); }
    @Override public String syncName() { return "whenArticleListByAuthorsListedThenWebRespondForViewFeed"; }
    @Override public SyncTrigger trigger() { return new SyncTrigger(ArticleConcept.IRI, "listByAuthors", null); }
    @Override protected String whereClause() {
        return "?_when_1 :concept <%s> ; :name \"listByAuthors\" ; :flow ?_flow ; :count ?_count .\n<< ?_when_1 :outcome \"Listed\" >> :flow ?_flow .".formatted(ArticleConcept.IRI);
    }
    @Override protected String thenBindings() {
        return "?_then_1 :concept <%s> ; :name \"respond\" ; :input [ :statusCode 200 ; :count ?_count ] .".formatted(WEB_IRI);
    }
}
