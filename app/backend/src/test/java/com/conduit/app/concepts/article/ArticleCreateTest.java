package com.conduit.app.concepts.article;

import com.conduit.app.ConceptTestBase;
import com.conduit.app.engine.RdfVocabulary;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ArticleCreate")
class ArticleCreateTest extends ConceptTestBase {
    private ArticleConcept concept;
    private int counter = 0;
    private String lastIri;

    private String freshIri() { counter++; lastIri = RdfVocabulary.ACTION_NODE_PREFIX + "art-test-" + counter; return lastIri; }

    private void init() { concept = new ArticleConcept(log, bus); }

    private void writeInvocation(String title) {
        String iri = freshIri();
        log.update("PREFIX : <" + RdfVocabulary.ACTION_SCHEMA_IRI + ">\nINSERT DATA { GRAPH <" + RdfVocabulary.ACTION_GRAPH_IRI + "> {\n" +
            "<" + iri + "> :concept <" + ArticleConcept.IRI + "> ; :name \"create\" ; :input _:i ; :flow <" + flow.mintFlowToken() + "> .\n" +
            "_:i :title \"" + title + "\" ; :authorId \"u1\" .\n} }");
    }

    private String readOutcome() {
        List<Map<String, String>> rows = log.select("PREFIX : <" + RdfVocabulary.ACTION_SCHEMA_IRI + ">\n" +
            "SELECT ?o WHERE { GRAPH <" + RdfVocabulary.ACTION_GRAPH_IRI + "> { << <" + lastIri + "> :outcome ?o >> :flow ?_ } }");
        return rows.isEmpty() ? null : rows.get(0).get("o");
    }

    @Test void shouldCreateArticleWithValidTitle() {
        init(); writeInvocation("My Test Article"); concept.pollAll();
        assertEquals("Created", readOutcome());
    }

    @Test void shouldRefuseBlankTitle() {
        init(); writeInvocation(""); concept.pollAll();
        assertEquals("refused", readOutcome());
    }
}
