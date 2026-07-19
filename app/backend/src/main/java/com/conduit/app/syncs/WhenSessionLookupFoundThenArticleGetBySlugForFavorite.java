package com.conduit.app.syncs;
import com.conduit.app.concepts.article.ArticleConcept;
import com.conduit.app.concepts.session.SessionConcept;
import com.conduit.app.engine.*;
import jakarta.inject.*;
@Singleton
public final class WhenSessionLookupFoundThenArticleGetBySlugForFavorite extends SyncAgent {
    @Inject public WhenSessionLookupFoundThenArticleGetBySlugForFavorite(ActionLog l) { super(l); }
    @Override public String syncName() { return "whenSessionLookupFoundThenArticleGetBySlugForFavorite"; }
    @Override public SyncTrigger trigger() { return new SyncTrigger(SessionConcept.IRI, "lookup", null); }
    @Override protected String whereClause() {
        return "?_when_1 :concept <%s> ; :name \"lookup\" ; :flow ?_flow .\n<< ?_when_1 :outcome \"FOUND\" >> :flow ?_flow .\n?_req :concept <%s> ; :name \"request\" ; :flow ?_flow ; :input ?_inp .\n?_inp :slug ?_slug .".formatted(SessionConcept.IRI, FlowManager.WEB_CONCEPT_IRI);
    }
    @Override protected String thenBindings() {
        return "?_then_1 :concept <%s> ; :name \"getBySlug\" ; :input [ :slug ?_slug ] .".formatted(ArticleConcept.IRI);
    }
}
