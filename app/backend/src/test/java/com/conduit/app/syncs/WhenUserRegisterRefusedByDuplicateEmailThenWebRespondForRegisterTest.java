package com.conduit.app.syncs;

import com.conduit.app.ConceptTestBase;
import com.conduit.app.concepts.user.UserConcept;
import com.conduit.app.engine.FlowManager;
import com.conduit.app.engine.RdfVocabulary;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("WhenUserRegisterRefusedByDuplicateEmailThenWebRespondForRegister")
class WhenUserRegisterRefusedByDuplicateEmailThenWebRespondForRegisterTest extends ConceptTestBase {

    private static final String FLOW_TOKEN = RdfVocabulary.FLOW_TOKEN_PREFIX + "dup-email-reg-1";
    private static final String TRIGGER_IRI = RdfVocabulary.ACTION_NODE_PREFIX + "dup-email-trigger";

    @Nested
    @DisplayName("WhenDuplicateEmail")
    class WhenDuplicateEmail {

        @Test
        void shouldRespond409WhenDuplicateEmail() {
            writeCompletedTrigger();
            var sync = new WhenUserRegisterRefusedByDuplicateEmailThenWebRespondForRegister(log);
            sync.execute();

            var pending = findPendingInvocation(FlowManager.WEB_CONCEPT_IRI, "respond");
            assertNotNull(pending, "Web.respond should be scheduled");
            assertEquals("409", pending.get("statusCode"));
        }
    }

    private void writeCompletedTrigger() {
        log.update(
            "PREFIX : <" + RdfVocabulary.ACTION_SCHEMA_IRI + ">\n" +
            "INSERT DATA {\n" +
            "  GRAPH <" + RdfVocabulary.ACTION_GRAPH_IRI + "> {\n" +
            "    <" + TRIGGER_IRI + "> :concept <" + UserConcept.IRI + "> ;\n" +
            "                     :name    \"register\" ;\n" +
            "                     :flow    <" + FLOW_TOKEN + "> ;\n" +
            "                     :refusalReason  \"email already taken: existing@test.com\" .\n" +
            "    << <" + TRIGGER_IRI + "> :outcome \"refused\" >> :flow <" + FLOW_TOKEN + "> .\n" +
            "  }\n" +
            "}\n");
    }

    private Map<String, String> findPendingInvocation(String conceptIri, String actionName) {
        List<Map<String, String>> rows = log.select(
            "PREFIX : <" + RdfVocabulary.ACTION_SCHEMA_IRI + ">\n" +
            "SELECT ?action ?predicate ?value\n" +
            "WHERE {\n" +
            "  GRAPH <" + RdfVocabulary.ACTION_GRAPH_IRI + "> {\n" +
            "    ?action :concept <" + conceptIri + "> ;\n" +
            "            :name    \"" + actionName + "\" ;\n" +
            "            :input   ?input ;\n" +
            "            :flow    <" + FLOW_TOKEN + "> .\n" +
            "    ?input ?predicate ?value .\n" +
            "  }\n" +
            "}\n");
        if (rows.isEmpty()) return null;
        Map<String, String> result = new java.util.LinkedHashMap<>();
        for (var row : rows) {
            result.put(row.get("predicate").substring(RdfVocabulary.ACTION_SCHEMA_IRI.length()),
                       row.get("value"));
        }
        return result;
    }
}
