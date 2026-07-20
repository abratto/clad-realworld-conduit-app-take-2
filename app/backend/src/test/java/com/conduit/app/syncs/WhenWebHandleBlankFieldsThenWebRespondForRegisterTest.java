package com.conduit.app.syncs;

import com.conduit.app.ConceptTestBase;
import com.conduit.app.engine.FlowManager;
import com.conduit.app.engine.RdfVocabulary;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("WhenWebHandleBlankFieldsThenWebRespondForRegister")
class WhenWebHandleBlankFieldsThenWebRespondForRegisterTest extends ConceptTestBase {

    private static final String FLOW_TOKEN = RdfVocabulary.FLOW_TOKEN_PREFIX + "web-refused-reg-1";
    private static final String REQUEST_IRI = RdfVocabulary.ACTION_NODE_PREFIX + "web-refused-reg-req";
    private static final String INPUT_IRI = REQUEST_IRI + "/input";
    private static final String HANDLE_IRI = RdfVocabulary.ACTION_NODE_PREFIX + "web-refused-reg-handle";
    private static final String HANDLE_INPUT_IRI = HANDLE_IRI + "/input";

    @Nested
    @DisplayName("WhenBlankField")
    class WhenBlankField {

        @Test
        void shouldRespond422WhenBlankField() {
            writeCompletedTrigger();
            var sync = new WhenWebHandleBlankFieldsThenWebRespondForRegister(log);
            sync.execute();

            var pending = findPendingInvocation(FlowManager.WEB_CONCEPT_IRI, "respond");
            assertNotNull(pending, "Web.respond should be scheduled");
            assertTrue(pending.containsKey("statusCode"));
            assertEquals("422", pending.get("statusCode"));
        }
    }

    private void writeCompletedTrigger() {
        log.update(
            "PREFIX : <" + RdfVocabulary.ACTION_SCHEMA_IRI + ">\n" +
            "INSERT DATA {\n" +
            "  GRAPH <" + RdfVocabulary.ACTION_GRAPH_IRI + "> {\n" +
            "    <" + REQUEST_IRI + "> :concept <" + FlowManager.WEB_CONCEPT_IRI + "> ;\n" +
            "                     :name    \"request\" ;\n" +
            "                     :input   <" + INPUT_IRI + "> ;\n" +
            "                     :flow    <" + FLOW_TOKEN + "> .\n" +
            "    <" + REQUEST_IRI + "> :outcome \"received\" .\n" +
            "    <" + INPUT_IRI + "> :route \"/api/users\" .\n" +
            "    <" + HANDLE_IRI + "> :concept <" + FlowManager.WEB_CONCEPT_IRI + "> ;\n" +
            "                     :name    \"handle\" ;\n" +
            "                     :input   <" + HANDLE_INPUT_IRI + "> ;\n" +
            "                     :flow    <" + FLOW_TOKEN + "> .\n" +
            "    <" + HANDLE_IRI + "> :outcome \"refused\" .\n" +
            "    <" + HANDLE_INPUT_IRI + "> :route \"/api/users\" .\n" +
            "    << <" + HANDLE_IRI + "> :outcome \"refused\" >> :flow <" + FLOW_TOKEN + "> .\n" +
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
