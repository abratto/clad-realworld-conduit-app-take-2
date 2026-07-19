package com.conduit.app.syncs;
import com.conduit.app.engine.*;
import jakarta.inject.*;
@Singleton
public final class WhenFavoriteFavoritedThenWebRespondForFavorite extends SyncAgent {
    private static final String WEB_IRI = FlowManager.WEB_CONCEPT_IRI;
    @Inject public WhenFavoriteFavoritedThenWebRespondForFavorite(ActionLog l) { super(l); }
    @Override public String syncName() { return "whenFavoriteFavoritedThenWebRespondForFavorite"; }
    @Override public SyncTrigger trigger() { return new SyncTrigger("https://clad.dev/concept/favorite", "favorite", null); }
    @Override protected String whereClause() {
        return "?_when_1 :concept <https://clad.dev/concept/favorite> ; :name \"favorite\" ; :flow ?_flow .\n<< ?_when_1 :outcome \"Favorited\" >> :flow ?_flow .";
    }
    @Override protected String thenBindings() {
        return "?_then_1 :concept <%s> ; :name \"respond\" ; :input [ :statusCode 200 ; :body \"{\\\"article\\\":{\\\"favorited\\\":true}}\" ] .".formatted(WEB_IRI);
    }
}
