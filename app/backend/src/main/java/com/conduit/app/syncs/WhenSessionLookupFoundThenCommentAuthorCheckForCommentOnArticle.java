package com.conduit.app.syncs;

import com.conduit.app.concepts.session.SessionConcept;
import com.conduit.app.engine.ActionLog;
import com.conduit.app.engine.FlowManager;
import com.conduit.app.engine.SyncAgent;
import com.conduit.app.engine.SyncMetadata;
import com.conduit.app.engine.SyncTrigger;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@SyncMetadata(flow = "Comment", step = 5, triggeredBy = "Session/lookup[FOUND]", fires = "Comment/authorCheck")
@Singleton
public final class WhenSessionLookupFoundThenCommentAuthorCheckForCommentOnArticle extends SyncAgent {
    private static final String WEB_IRI = FlowManager.WEB_CONCEPT_IRI;
    private static final String COMMENT_IRI = "https://clad.dev/concept/comment";
    private static final String COMMENT_ROUTE = "/api/articles";
    @Inject public WhenSessionLookupFoundThenCommentAuthorCheckForCommentOnArticle(ActionLog l) { super(l); }
    @Override public String syncName() { return "whenSessionLookupFoundThenCommentAuthorCheckForCommentOnArticle"; }
    @Override public SyncTrigger trigger() { return new SyncTrigger(SessionConcept.IRI, "lookup", null); }
    @Override protected String whereClause() {
        return "?_when_1 :concept <%s> ; :name \"lookup\" ; :flow ?_flow ; :userId ?_userId .\n<< ?_when_1 :outcome \"FOUND\" >> :flow ?_flow .\n?_req :concept <%s> ; :name \"request\" ; :flow ?_flow ; :input ?_inp .\n?_inp :route ?_route ; :commentId ?_commentId .".formatted(SessionConcept.IRI, WEB_IRI);
    }
    @Override protected String thenBindings() {
        return "?_then_1 :concept <%s> ; :name \"authorCheck\" ; :input [ :memberId ?_userId ; :commentId ?_commentId ] .".formatted(COMMENT_IRI);
    }
    @Override protected String parameterizeSparql(String sparql) {
        return bindLiteral(sparql, "_route", COMMENT_ROUTE);
    }
}
