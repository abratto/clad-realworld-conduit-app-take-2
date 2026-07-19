package com.conduit.app.syncs;
import com.conduit.app.engine.*;
import jakarta.inject.*;
@Singleton
public final class WhenArticleGetBySlugRefusedThenWebRespondForReadArticle extends SyncAgent {
    private static final String WEB_IRI = FlowManager.WEB_CONCEPT_IRI;
    private static final String ART_IRI = "https://clad.dev/concept/article";
    @Inject public WhenArticleGetBySlugRefusedThenWebRespondForReadArticle(ActionLog l) { super(l); }
    @Override public String syncName() { return "whenArticleGetBySlugRefusedThenWebRespondForReadArticle"; }
    @Override public SyncTrigger trigger() { return new SyncTrigger(ART_IRI, "getBySlug", null); }
    @Override protected String whereClause() {
        return "?_when_1 :concept <%s> ; :name \"getBySlug\" ; :flow ?_flow .\n<< ?_when_1 :outcome \"refused\" >> :flow ?_flow .".formatted(ART_IRI);
    }
    @Override protected String thenBindings() {
        return "?_then_1 :concept <%s> ; :name \"respond\" ; :input [ :statusCode 404 ; :message \"not found\" ] .".formatted(WEB_IRI);
    }
}
