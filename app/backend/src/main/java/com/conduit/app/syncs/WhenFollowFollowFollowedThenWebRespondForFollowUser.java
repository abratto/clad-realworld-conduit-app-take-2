package com.conduit.app.syncs;
import com.conduit.app.engine.*;
import jakarta.inject.*;
@Singleton
public final class WhenFollowFollowFollowedThenWebRespondForFollowUser extends SyncAgent {
    private static final String WEB_IRI = FlowManager.WEB_CONCEPT_IRI;
    @Inject public WhenFollowFollowFollowedThenWebRespondForFollowUser(ActionLog l) { super(l); }
    @Override public String syncName() { return "whenFollowFollowFollowedThenWebRespondForFollowUser"; }
    @Override public SyncTrigger trigger() { return new SyncTrigger("https://clad.dev/concept/follow", "follow", null); }
    @Override protected String whereClause() {
        return "?_when_1 :concept <https://clad.dev/concept/follow> ; :name \"follow\" ; :flow ?_flow .\n<< ?_when_1 :outcome \"Followed\" >> :flow ?_flow .";
    }
    @Override protected String thenBindings() {
        return "?_then_1 :concept <%s> ; :name \"respond\" ; :input [ :statusCode 200 ; :body \"{\\\"profile\\\":{\\\"following\\\":true}}\" ] .".formatted(WEB_IRI);
    }
}
