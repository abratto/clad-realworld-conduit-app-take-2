package com.conduit.app.concepts.user;

import com.conduit.app.ConceptTestBase;
import com.conduit.app.engine.RdfVocabulary;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserGetProfile")
class UserGetProfileTest extends ConceptTestBase {

    private UserConcept concept;
    private int actionCounter = 0;
    private String lastActionIri;

    private String freshActionIri() {
        actionCounter++;
        lastActionIri = RdfVocabulary.ACTION_NODE_PREFIX + "getprofile-test-" + actionCounter;
        return lastActionIri;
    }

    private void initConcept() { concept = new UserConcept(log, bus); }

    private void writePendingInvocation(String userId) {
        String actionIri = freshActionIri();
        log.update("PREFIX : <" + RdfVocabulary.ACTION_SCHEMA_IRI + ">\n" +
            "INSERT DATA { GRAPH <" + RdfVocabulary.ACTION_GRAPH_IRI + "> {\n" +
            "  <" + actionIri + "> :concept <" + UserConcept.IRI + "> ;\n" +
            "                   :name    \"getProfile\" ;\n" +
            "                   :input   _:inp ;\n" +
            "                   :flow    <" + flow.mintFlowToken() + "> .\n" +
            "  _:inp :userId \"" + userId + "\" .\n" +
            "} }");
    }

    private String readOutcome() {
        List<Map<String, String>> rows = log.select(
            "PREFIX : <" + RdfVocabulary.ACTION_SCHEMA_IRI + ">\n" +
            "SELECT ?_outcome WHERE {\n" +
            "  GRAPH <" + RdfVocabulary.ACTION_GRAPH_IRI + "> {\n" +
            "    << <" + lastActionIri + "> :outcome ?_outcome >> :flow ?_flow .\n" +
            "  }\n" +
            "}\n");
        return rows.isEmpty() ? null : rows.get(0).get("_outcome");
    }

    @Nested @DisplayName("WhenUserExists")
    class WhenUserExists {
        @Test
        void shouldReturnProfileWhenUserExists() {
            initConcept();
            concept.seedUser("u1", "ada");
            concept.seedEmail("u1", "ada@test.com");
            writePendingInvocation("u1");
            concept.pollAll();
            assertEquals("FOUND", readOutcome());
        }
    }
}
