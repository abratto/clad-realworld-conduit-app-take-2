package com.conduit.app.syncs;
import com.conduit.app.engine.*;
import jakarta.inject.*;
@Singleton
public final class WhenArticleGetBySlugFoundThenCommentListByArticleForViewComments extends SyncAgent {
    @Inject public WhenArticleGetBySlugFoundThenCommentListByArticleForViewComments(ActionLog l) { super(l); }
    @Override public String syncName() { return "whenArticleGetBySlugFoundThenCommentListByArticleForViewComments"; }
    @Override public SyncTrigger trigger() { return new SyncTrigger("https://clad.dev/concept/article", "getBySlug", null); }
    @Override protected String whereClause() {
        return "?_when_1 :concept <https://clad.dev/concept/article> ; :name \"getBySlug\" ; :flow ?_flow .\n<< ?_when_1 :outcome \"FOUND\" >> :flow ?_flow .";
    }
    @Override protected String thenBindings() {
        return "?_then_1 :concept <https://clad.dev/concept/comment> ; :name \"listByArticle\" ; :input [ :articleId ?_articleId ] .";
    }
}
