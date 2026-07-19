package com.conduit.app.concepts.comment;

import com.conduit.app.ConceptTestBase;
import com.conduit.app.engine.RdfVocabulary;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CommentAdd")
class CommentAddTest extends ConceptTestBase {
    private CommentConcept concept;
    private int counter = 0;
    private String lastIri;

    private String freshIri() { counter++; lastIri = RdfVocabulary.ACTION_NODE_PREFIX + "comment-test-" + counter; return lastIri; }

    private void init() { concept = new CommentConcept(log, bus); }

    private void writeInvocation(String body) {
        String iri = freshIri();
        log.update("PREFIX : <" + RdfVocabulary.ACTION_SCHEMA_IRI + ">\nINSERT DATA { GRAPH <" + RdfVocabulary.ACTION_GRAPH_IRI + "> {\n" +
            "<" + iri + "> :concept <" + CommentConcept.IRI + "> ; :name \"add\" ; :input _:i ; :flow <" + flow.mintFlowToken() + "> .\n" +
            "_:i :body \"" + body + "\" .\n} }");
    }

    private String readOutcome() {
        List<Map<String, String>> rows = log.select("PREFIX : <" + RdfVocabulary.ACTION_SCHEMA_IRI + ">\n" +
            "SELECT ?o WHERE { GRAPH <" + RdfVocabulary.ACTION_GRAPH_IRI + "> { << <" + lastIri + "> :outcome ?o >> :flow ?_ } }");
        return rows.isEmpty() ? null : rows.get(0).get("o");
    }

    @Test void shouldAddCommentWithValidBody() {
        init(); writeInvocation("Great article!"); concept.pollAll();
        assertEquals("Added", readOutcome());
    }

    @Test void shouldRefuseBlankBody() {
        init(); writeInvocation(""); concept.pollAll();
        assertEquals("refused", readOutcome());
    }
}
