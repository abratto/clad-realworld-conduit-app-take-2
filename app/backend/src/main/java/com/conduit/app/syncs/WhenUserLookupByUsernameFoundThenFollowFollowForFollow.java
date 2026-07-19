package com.conduit.app.syncs;
import com.conduit.app.concepts.user.UserConcept;
import com.conduit.app.engine.*;
import jakarta.inject.*;
@Singleton
public final class WhenUserLookupByUsernameFoundThenFollowFollowForFollow extends SyncAgent {
    @Inject public WhenUserLookupByUsernameFoundThenFollowFollowForFollow(ActionLog l) { super(l); }
    @Override public String syncName() { return "whenUserLookupByUsernameFoundThenFollowFollowForFollow"; }
    @Override public SyncTrigger trigger() { return new SyncTrigger(UserConcept.IRI, "lookupByUsername", null); }
    @Override protected String whereClause() {
        return "?_when_1 :concept <%s> ; :name \"lookupByUsername\" ; :flow ?_flow .\n<< ?_when_1 :outcome \"FOUND\" >> :flow ?_flow .".formatted(UserConcept.IRI);
    }
    @Override protected String thenBindings() {
        return "?_then_1 :concept <https://clad.dev/concept/follow> ; :name \"follow\" ; :input [ :followerId \"_\" ; :profileId \"_\" ] .";
    }
}
