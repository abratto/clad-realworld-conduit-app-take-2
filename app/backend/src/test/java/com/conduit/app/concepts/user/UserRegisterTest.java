package com.conduit.app.concepts.user;

import com.conduit.app.ConceptTestBase;
import com.conduit.app.engine.RdfVocabulary;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserRegister")
class UserRegisterTest extends ConceptTestBase {

    private UserConcept concept;
    private int actionCounter = 0;
    private String lastActionIri;

    private String freshActionIri() {
        actionCounter++;
        lastActionIri = RdfVocabulary.ACTION_NODE_PREFIX + "register-test-" + actionCounter;
        return lastActionIri;
    }

    private void initConcept() {
        concept = new UserConcept(log, bus);
    }

    private void writePendingInvocation(String username, String email, String password) {
        String actionIri = freshActionIri();
        log.update(
            "PREFIX : <" + RdfVocabulary.ACTION_SCHEMA_IRI + ">\n" +
            "INSERT DATA {\n" +
            "  GRAPH <" + RdfVocabulary.ACTION_GRAPH_IRI + "> {\n" +
            "    <" + actionIri + "> :concept <" + UserConcept.IRI + "> ;\n" +
            "                     :name    \"register\" ;\n" +
            "                     :input   _:inp ;\n" +
            "                     :flow    <" + flow.mintFlowToken() + "> .\n" +
            "    _:inp :username \"" + username + "\" ;\n" +
            "           :email    \"" + email + "\" ;\n" +
            "           :password \"" + password + "\" .\n" +
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
    @DisplayName("WhenUniqueCredentials")
    class WhenUniqueCredentials {

        @Test
        void shouldRegisterUserWithUniqueUsernameAndEmail() {
            initConcept();
            writePendingInvocation("jdoe", "jdoe@test.com", "secret123");

            concept.pollAll();

            assertEquals("Registered", readOutcome());
            assertNotNull(readField("userId"));
            assertNotNull(readField("username"));
            assertEquals("jdoe", readField("username"));
        }
    }

    @Nested
    @DisplayName("WhenUsernameTaken")
    class WhenUsernameTaken {

        @Test
        void shouldRefuseWhenUsernameAlreadyExists() {
            initConcept();
            concept.seedUser("existing-001", "existing_user");
            writePendingInvocation("existing_user", "new@test.com", "secret123");

            concept.pollAll();

            assertEquals("refused", readOutcome());
            assertNotNull(readField("refusalReason"));
        }
    }

    @Nested
    @DisplayName("WhenEmailTaken")
    class WhenEmailTaken {

        @Test
        void shouldRefuseWhenEmailAlreadyExists() {
            initConcept();
            concept.seedUser("existing-001", "some_user");
            log.update(
                "PREFIX : <" + RdfVocabulary.ACTION_SCHEMA_IRI + ">\n" +
                "PREFIX u: <https://clad.dev/concept/user#>\n" +
                "INSERT DATA { GRAPH <concept:user> {\n" +
                "  <https://clad.dev/concept/user#user/existing-001> u:email \"existing@test.com\" .\n" +
                "} }");
            writePendingInvocation("new_user", "existing@test.com", "secret123");

            concept.pollAll();

            assertEquals("refused", readOutcome());
            assertNotNull(readField("refusalReason"));
        }
    }
}
