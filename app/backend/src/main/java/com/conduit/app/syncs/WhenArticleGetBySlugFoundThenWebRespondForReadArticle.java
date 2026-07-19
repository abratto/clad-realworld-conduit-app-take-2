package com.conduit.app.syncs;
import com.conduit.app.engine.*;
import jakarta.inject.*;
@Singleton
public final class WhenArticleGetBySlugFoundThenWebRespondForReadArticle extends SyncAgent {
    private static final String WEB_IRI = FlowManager.WEB_CONCEPT_IRI;
    private static final String ART_IRI = "https://clad.dev/concept/article";
    @Inject public WhenArticleGetBySlugFoundThenWebRespondForReadArticle(ActionLog l) { super(l); }
    @Override public String syncName() { return "whenArticleGetBySlugFoundThenWebRespondForReadArticle"; }
    @Override public SyncTrigger trigger() { return new SyncTrigger(ART_IRI, "getBySlug", null); }
    @Override protected String whereClause() {
        return "?_when_1 :concept <%s> ; :name \"getBySlug\" ; :flow ?_flow .\n<< ?_when_1 :outcome \"FOUND\" >> :flow ?_flow .".formatted(ART_IRI);
    }
    @Override protected String thenBindings() {
        return "?_then_1 :concept <%s> ; :name \"respond\" ; :input [ :statusCode 200 ; :slug ?_slug ] .".formatted(WEB_IRI);
    }
}
