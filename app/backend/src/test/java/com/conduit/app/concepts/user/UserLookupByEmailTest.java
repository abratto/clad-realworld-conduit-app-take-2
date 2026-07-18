package com.conduit.app.concepts.user;

import com.conduit.app.ConceptTestBase;
import com.conduit.app.engine.RdfVocabulary;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserLookupByEmail")
class UserLookupByEmailTest extends ConceptTestBase {

    private UserConcept concept;
    private int actionCounter = 0;
    private String lastActionIri;

    private String freshActionIri() {
        actionCounter++;
        lastActionIri = RdfVocabulary.ACTION_NODE_PREFIX + "lookup-email-test-" + actionCounter;
        return lastActionIri;
    }

    private void initConcept() {
        concept = new UserConcept(log, bus);
    }

    private void writePendingInvocation(String email) {
        String actionIri = freshActionIri();
        log.update(
            "PREFIX : <" + RdfVocabulary.ACTION_SCHEMA_IRI + ">\n" +
            "INSERT DATA {\n" +
            "  GRAPH <" + RdfVocabulary.ACTION_GRAPH_IRI + "> {\n" +
            "    <" + actionIri + "> :concept <" + UserConcept.IRI + "> ;\n" +
            "                     :name    \"lookupByEmail\" ;\n" +
            "                     :input   _:inp ;\n" +
            "                     :flow    <" + flow.mintFlowToken() + "> .\n" +
            "    _:inp :email \"" + email + "\" .\n" +
            "  }\n" +
            "}\n");
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

    private String readField(String fieldName) {
        List<Map<String, String>> rows = log.select(
            "PREFIX : <" + RdfVocabulary.ACTION_SCHEMA_IRI + ">\n" +
            "SELECT ?value WHERE {\n" +
            "  GRAPH <" + RdfVocabulary.ACTION_GRAPH_IRI + "> {\n" +
            "    <" + lastActionIri + "> :" + fieldName + " ?value .\n" +
            "  }\n" +
            "}\n");
        return rows.isEmpty() ? null : rows.get(0).get("value");
    }

    @Nested
    @DisplayName("WhenEmailFound")
    class WhenEmailFound {

        @Test
        void shouldReturnUserIdWhenEmailFound() {
            initConcept();
            concept.seedUser("ada-0001", "ada");
            concept.seedEmail("ada-0001", "ada@test.com");
            writePendingInvocation("ada@test.com");

            concept.pollAll();

            assertEquals("FOUND", readOutcome());
            assertNotNull(readField("email"));
            assertEquals("ada@test.com", readField("email"));
            assertNotNull(readField("userId"));
            assertEquals("ada-0001", readField("userId"));
        }
    }

    @Nested
    @DisplayName("WhenEmailUnknown")
    class WhenEmailUnknown {

        @Test
        void shouldRefuseWhenEmailUnknown() {
            initConcept();
            writePendingInvocation("nobody@test.com");

            concept.pollAll();

            assertEquals("refused", readOutcome());
            assertNotNull(readField("refusalReason"));
        }
    }
}
