package com.conduit.app.syncs;

import com.conduit.app.engine.ActionLog;
import com.conduit.app.engine.FlowManager;
import com.conduit.app.engine.SyncAgent;
import com.conduit.app.engine.SyncMetadata;
import com.conduit.app.engine.SyncTrigger;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@SyncMetadata(flow = "Comment", step = 6, triggeredBy = "Comment/authorCheck[IsAuthor]", fires = "Comment/delete")
@Singleton
public final class WhenCommentAuthorCheckIsAuthorThenCommentDeleteForCommentOnArticle extends SyncAgent {
    private static final String WEB_IRI = FlowManager.WEB_CONCEPT_IRI;
    private static final String COMMENT_IRI = "https://clad.dev/concept/comment";
    @Inject public WhenCommentAuthorCheckIsAuthorThenCommentDeleteForCommentOnArticle(ActionLog l) { super(l); }
    @Override public String syncName() { return "whenCommentAuthorCheckIsAuthorThenCommentDeleteForCommentOnArticle"; }
    @Override public SyncTrigger trigger() { return new SyncTrigger(COMMENT_IRI, "authorCheck", null); }
    @Override protected String whereClause() {
        return "?_when_1 :concept <%s> ; :name \"authorCheck\" ; :flow ?_flow ; :input ?_ac_inp .\n?_ac_inp :commentId ?_commentId .\n<< ?_when_1 :outcome \"IsAuthor\" >> :flow ?_flow .\n?_req :concept <%s> ; :name \"request\" ; :flow ?_flow ; :input ?_inp .\n?_inp :slug ?_slug .".formatted(COMMENT_IRI, WEB_IRI);
    }
    @Override protected String thenBindings() {
        return "?_then_1 :concept <%s> ; :name \"delete\" ; :input [ :commentId ?_commentId ] .".formatted(COMMENT_IRI);
    }
}
