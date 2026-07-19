package com.conduit.app.syncs;

import com.conduit.app.engine.ActionLog;
import com.conduit.app.engine.FlowManager;
import com.conduit.app.engine.SyncAgent;
import com.conduit.app.engine.SyncMetadata;
import com.conduit.app.engine.SyncTrigger;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@SyncMetadata(flow = "Comment", step = 6, triggeredBy = "Comment/add[refused]", fires = "Web/respond[422]")
@Singleton
public final class WhenCommentAddRefusedBlankBodyThenWebRespondForComment extends SyncAgent {
    private static final String WEB_IRI = FlowManager.WEB_CONCEPT_IRI;
    private static final String COMMENT_IRI = "https://clad.dev/concept/comment";
    private static final String COMMENT_ROUTE = "/api/articles";
    @Inject public WhenCommentAddRefusedBlankBodyThenWebRespondForComment(ActionLog l) { super(l); }
    @Override public String syncName() { return "whenCommentAddRefusedBlankBodyThenWebRespondForComment"; }
    @Override public SyncTrigger trigger() { return new SyncTrigger(COMMENT_IRI, "add", null); }
    @Override protected String whereClause() {
        return "?_when_1 :concept <%s> ; :name \"add\" ; :flow ?_flow .\n<< ?_when_1 :outcome \"refused\" >> :flow ?_flow .\n?_req :concept <%s> ; :name \"request\" ; :flow ?_flow ; :input ?_inp .\n?_inp :route ?_route .".formatted(COMMENT_IRI, WEB_IRI);
    }
    @Override protected String thenBindings() {
        return "?_then_1 :concept <%s> ; :name \"respond\" ; :input [ :statusCode 422 ; :message \"blank body\" ] .".formatted(WEB_IRI);
    }
    @Override protected String parameterizeSparql(String sparql) {
        return bindLiteral(sparql, "_route", COMMENT_ROUTE);
    }
}
