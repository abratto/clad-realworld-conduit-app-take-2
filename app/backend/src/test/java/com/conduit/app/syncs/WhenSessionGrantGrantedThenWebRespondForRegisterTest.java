package com.conduit.app.syncs;

import com.conduit.app.ConceptTestBase;
import com.conduit.app.concepts.session.SessionConcept;
import com.conduit.app.engine.FlowManager;
import com.conduit.app.engine.RdfVocabulary;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("WhenSessionGrantGrantedThenWebRespondForRegister")
class WhenSessionGrantGrantedThenWebRespondForRegisterTest extends ConceptTestBase {

    private static final String FLOW_TOKEN = RdfVocabulary.FLOW_TOKEN_PREFIX + "session-granted-reg-1";
    private static final String TRIGGER_IRI = RdfVocabulary.ACTION_NODE_PREFIX + "session-granted-trigger";
    private static final String WEB_REQ_IRI = RdfVocabulary.ACTION_NODE_PREFIX + "web-req-for-session";
    private static final String WEB_INPUT_IRI = WEB_REQ_IRI + "/input";

    @Nested
    @DisplayName("WhenGranted")
    class WhenGranted {

        @Test
        void shouldFireWebRespondWhenGranted() {
            writeCompletedTrigger();
            var sync = new WhenSessionGrantGrantedThenWebRespondForRegister(log);
            sync.execute();

            var pending = findPendingInvocation(FlowManager.WEB_CONCEPT_IRI, "respond");
            assertNotNull(pending, "Web.respond should be scheduled");
        }
    }

    private void writeCompletedTrigger() {
        log.update(
            "PREFIX : <" + RdfVocabulary.ACTION_SCHEMA_IRI + ">\n" +
            "INSERT DATA {\n" +
            "  GRAPH <" + RdfVocabulary.ACTION_GRAPH_IRI + "> {\n" +
            "    <" + WEB_REQ_IRI + "> :concept <" + FlowManager.WEB_CONCEPT_IRI + "> ;\n" +
            "                      :name    \"request\" ;\n" +
            "                      :input   <" + WEB_INPUT_IRI + "> ;\n" +
            "                      :flow    <" + FLOW_TOKEN + "> .\n" +
            "    <" + WEB_INPUT_IRI + "> :route    \"/api/users\" ;\n" +
            "                       :username \"jdoe\" ;\n" +
            "                       :email    \"jdoe@test.com\" .\n" +
            "    <" + TRIGGER_IRI + "> :concept <" + SessionConcept.IRI + "> ;\n" +
            "                      :name    \"grant\" ;\n" +
            "                      :userId  \"user-001\" ;\n" +
            "                      :sessionToken  \"mock-token-uuid\" ;\n" +
            "                      :flow    <" + FLOW_TOKEN + "> .\n" +
            "    << <" + TRIGGER_IRI + "> :outcome \"Granted\" >> :flow <" + FLOW_TOKEN + "> .\n" +
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
