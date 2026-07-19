package com.conduit.app.syncs;
import com.conduit.app.engine.*;
import jakarta.inject.*;
@Singleton
public final class WhenArticleGetBySlugRefusedThenWebRespondForViewComments extends SyncAgent {
    private static final String WEB_IRI = FlowManager.WEB_CONCEPT_IRI;
    @Inject public WhenArticleGetBySlugRefusedThenWebRespondForViewComments(ActionLog l) { super(l); }
    @Override public String syncName() { return "whenArticleGetBySlugRefusedThenWebRespondForViewComments"; }
    @Override public SyncTrigger trigger() { return new SyncTrigger("https://clad.dev/concept/article", "getBySlug", null); }
    @Override protected String whereClause() {
        return "?_when_1 :concept <https://clad.dev/concept/article> ; :name \"getBySlug\" ; :flow ?_flow .\n<< ?_when_1 :outcome \"refused\" >> :flow ?_flow .";
    }
    @Override protected String thenBindings() {
        return "?_then_1 :concept <%s> ; :name \"respond\" ; :input [ :statusCode 404 ; :message \"not found\" ] .".formatted(WEB_IRI);
    }
}
