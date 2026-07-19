package com.conduit.app.syncs;
import com.conduit.app.engine.*;
import jakarta.inject.*;
@Singleton
public final class WhenArticleGetBySlugFoundThenFavoriteFavoriteForFavorite extends SyncAgent {
    @Inject public WhenArticleGetBySlugFoundThenFavoriteFavoriteForFavorite(ActionLog l) { super(l); }
    @Override public String syncName() { return "whenArticleGetBySlugFoundThenFavoriteFavoriteForFavorite"; }
    @Override public SyncTrigger trigger() { return new SyncTrigger("https://clad.dev/concept/article", "getBySlug", null); }
    @Override protected String whereClause() {
        return "?_when_1 :concept <https://clad.dev/concept/article> ; :name \"getBySlug\" ; :flow ?_flow .\n<< ?_when_1 :outcome \"FOUND\" >> :flow ?_flow .";
    }
    @Override protected String thenBindings() {
        return "?_then_1 :concept <https://clad.dev/concept/favorite> ; :name \"favorite\" ; :input [ :userId \"_\" ; :articleId \"_\" ] .";
    }
}
