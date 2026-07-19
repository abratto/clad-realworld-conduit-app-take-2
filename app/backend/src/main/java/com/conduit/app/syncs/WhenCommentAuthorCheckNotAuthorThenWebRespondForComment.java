package com.conduit.app.syncs;

import com.conduit.app.engine.ActionLog;
import com.conduit.app.engine.FlowManager;
import com.conduit.app.engine.SyncAgent;
import com.conduit.app.engine.SyncMetadata;
import com.conduit.app.engine.SyncTrigger;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@SyncMetadata(flow = "Comment", step = 6, triggeredBy = "Comment/authorCheck[NotAuthor]", fires = "Web/respond[403]")
@Singleton
public final class WhenCommentAuthorCheckNotAuthorThenWebRespondForComment extends SyncAgent {
    private static final String WEB_IRI = FlowManager.WEB_CONCEPT_IRI;
    private static final String COMMENT_IRI = "https://clad.dev/concept/comment";
    private static final String COMMENT_ROUTE = "/api/articles";
    @Inject public WhenCommentAuthorCheckNotAuthorThenWebRespondForComment(ActionLog l) { super(l); }
    @Override public String syncName() { return "whenCommentAuthorCheckNotAuthorThenWebRespondForComment"; }
    @Override public SyncTrigger trigger() { return new SyncTrigger(COMMENT_IRI, "authorCheck", null); }
    @Override protected String whereClause() {
        return "?_when_1 :concept <%s> ; :name \"authorCheck\" ; :flow ?_flow .\n<< ?_when_1 :outcome \"NotAuthor\" >> :flow ?_flow .\n?_req :concept <%s> ; :name \"request\" ; :flow ?_flow ; :input ?_inp .\n?_inp :route ?_route .".formatted(COMMENT_IRI, WEB_IRI);
    }
    @Override protected String thenBindings() {
        return "?_then_1 :concept <%s> ; :name \"respond\" ; :input [ :statusCode 403 ; :message \"forbidden\" ] .".formatted(WEB_IRI);
    }
    @Override protected String parameterizeSparql(String sparql) {
        return bindLiteral(sparql, "_route", COMMENT_ROUTE);
    }
}
