package com.conduit.app.syncs;
import com.conduit.app.concepts.article.ArticleConcept;
import com.conduit.app.engine.*;
import jakarta.inject.*;
@Singleton
public final class WhenFollowGetFollowedUsersListedThenArticleListByAuthorsForViewFeed extends SyncAgent {
    @Inject public WhenFollowGetFollowedUsersListedThenArticleListByAuthorsForViewFeed(ActionLog l) { super(l); }
    @Override public String syncName() { return "whenFollowGetFollowedUsersListedThenArticleListByAuthorsForViewFeed"; }
    @Override public SyncTrigger trigger() { return new SyncTrigger("https://clad.dev/concept/follow", "getFollowedUsers", null); }
    @Override protected String whereClause() {
        return "?_when_1 :concept <https://clad.dev/concept/follow> ; :name \"getFollowedUsers\" ; :flow ?_flow .\n?_when_1 :followeeIds ?_followeeIds .\n<< ?_when_1 :outcome \"Listed\" >> :flow ?_flow .\n?_req :concept <%s> ; :name \"request\" ; :flow ?_flow ; :input ?_inp .\n?_inp :limit ?_limit ; :offset ?_offset .".formatted(FlowManager.WEB_CONCEPT_IRI);
    }
    @Override protected String thenBindings() {
        return "?_then_1 :concept <%s> ; :name \"listByAuthors\" ; :input [ :authorIds ?_followeeIds ; :limit ?_limit ; :offset ?_offset ] .".formatted(ArticleConcept.IRI);
    }
}
