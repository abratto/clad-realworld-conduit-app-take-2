package com.conduit.app.concepts.user;

import com.conduit.app.engine.ActionLog;
import com.conduit.app.engine.ActionRecord;
import com.conduit.app.engine.CompletionBus;
import com.conduit.app.engine.ConceptAgent;
import com.conduit.app.engine.RdfVocabulary;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.rdf.model.ResourceFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * The User concept: who exists in the system.
 *
 * <p>State lives in the named graph {@code concept:user}. Two actions:
 * <ul>
 *   <li>{@code register} — creates a user with username, email, hashed password.</li>
 *   <li>{@code lookupByUsername} — emits {@code outcome=FOUND|UNKNOWN}.</li>
 * </ul>
 */
@Singleton
public final class UserConcept extends ConceptAgent {

    /** IRI used in :concept triples. */
    public static final String IRI = "https://clad.dev/concept/user";

    private static final String GRAPH = RdfVocabulary.conceptGraph("user");
    private static final String PROFILE_NS = "https://clad.dev/concept/user#";

    @Inject
    public UserConcept(ActionLog actionLog, CompletionBus completionBus) {
        super(actionLog, completionBus);
    }

    @Override
    protected String conceptIRI() {
        return IRI;
    }

    @Override
    public void pollAll() {
        pollAndProcess("register");
        pollAndProcess("lookupByUsername");
    }

    @Override
    protected void processInvocation(ActionRecord invocation) {
        switch (invocation.actionName()) {
            case "register" -> doRegister(invocation);
            case "lookupByUsername" -> doLookup(invocation);
            default -> writeError(invocation, "unknown action: " + invocation.actionName());
        }
    }

    /** Test/seed helper to pre-populate the user graph with a username. */
    public void seedUser(String userId, String username) {
        var pss = new ParameterizedSparqlString();
        pss.setNsPrefix("u", PROFILE_NS);
        pss.setCommandText("INSERT DATA { GRAPH ?g { ?user u:username ?username } }");
        pss.setIri("g", GRAPH);
        pss.setIri("user", PROFILE_NS + "user/" + userId);
        pss.setLiteral("username", username);
        actionLog.update(pss.toString());
    }

    private void doRegister(ActionRecord invocation) {
        String username = invocation.binding("username");
        String email = invocation.binding("email");
        String password = invocation.binding("password");

        if (username == null || username.isBlank()) { writeRefusal(invocation, "blank username"); return; }
        if (email == null || email.isBlank()) { writeRefusal(invocation, "blank email"); return; }
        if (password == null || password.isBlank()) { writeRefusal(invocation, "blank password"); return; }

        if (existsByUsername(username)) {
            writeRefusal(invocation, "username already taken: " + username);
            return;
        }
        if (existsByEmail(email)) {
            writeRefusal(invocation, "email already taken: " + email);
            return;
        }

        String userId = UUID.randomUUID().toString();
        String passwordHash = sha256Hex(password);
        seedUser(userId, username);
        storeEmail(userId, email);
        storePasswordHash(userId, passwordHash);
        storeBio(userId, null);
        storeImage(userId, null);

        writeCompletion(invocation, Map.of(
                "outcome", ResourceFactory.createStringLiteral("Registered"),
                "userId", ResourceFactory.createStringLiteral(userId),
                "username", ResourceFactory.createStringLiteral(username)));
    }

    private void doLookup(ActionRecord invocation) {
        String username = invocation.binding("username");
        if (username == null) { writeError(invocation, "missing username"); return; }
        String userId = findUserIdByUsername(username);
        if (userId == null) {
            writeRefusal(invocation, "username not found: " + username);
        } else {
            writeCompletion(invocation, Map.of(
                    "outcome", ResourceFactory.createStringLiteral("FOUND"),
                    "userId", ResourceFactory.createStringLiteral(userId),
                    "username", ResourceFactory.createStringLiteral(username)));
        }
    }

    private void storeEmail(String userId, String email) {
        var pss = new ParameterizedSparqlString();
        pss.setNsPrefix("u", PROFILE_NS);
        pss.setCommandText("INSERT DATA { GRAPH ?g { ?user u:email ?email } }");
        pss.setIri("g", GRAPH);
        pss.setIri("user", PROFILE_NS + "user/" + userId);
        pss.setLiteral("email", email);
        actionLog.update(pss.toString());
    }

    private void storePasswordHash(String userId, String hash) {
        var pss = new ParameterizedSparqlString();
        pss.setNsPrefix("u", PROFILE_NS);
        pss.setCommandText("INSERT DATA { GRAPH ?g { ?user u:passwordHash ?hash } }");
        pss.setIri("g", GRAPH);
        pss.setIri("user", PROFILE_NS + "user/" + userId);
        pss.setLiteral("hash", hash);
        actionLog.update(pss.toString());
    }

    private void storeBio(String userId, String bio) {
        if (bio == null) return;
        var pss = new ParameterizedSparqlString();
        pss.setNsPrefix("u", PROFILE_NS);
        pss.setCommandText("INSERT DATA { GRAPH ?g { ?user u:bio ?bio } }");
        pss.setIri("g", GRAPH);
        pss.setIri("user", PROFILE_NS + "user/" + userId);
        pss.setLiteral("bio", bio);
        actionLog.update(pss.toString());
    }

    private void storeImage(String userId, String image) {
        if (image == null) return;
        var pss = new ParameterizedSparqlString();
        pss.setNsPrefix("u", PROFILE_NS);
        pss.setCommandText("INSERT DATA { GRAPH ?g { ?user u:image ?image } }");
        pss.setIri("g", GRAPH);
        pss.setIri("user", PROFILE_NS + "user/" + userId);
        pss.setLiteral("image", image);
        actionLog.update(pss.toString());
    }

    private boolean existsByUsername(String username) {
        var pss = new ParameterizedSparqlString();
        pss.setNsPrefix("u", PROFILE_NS);
        pss.setCommandText("ASK { GRAPH ?g { ?user u:username ?username } }");
        pss.setIri("g", GRAPH);
        pss.setLiteral("username", username);
        return actionLog.ask(pss.toString());
    }

    private boolean existsByEmail(String email) {
        var pss = new ParameterizedSparqlString();
        pss.setNsPrefix("u", PROFILE_NS);
        pss.setCommandText("ASK { GRAPH ?g { ?user u:email ?email } }");
        pss.setIri("g", GRAPH);
        pss.setLiteral("email", email);
        return actionLog.ask(pss.toString());
    }

    private String findUserIdByUsername(String username) {
        var pss = new ParameterizedSparqlString();
        pss.setNsPrefix("u", PROFILE_NS);
        pss.setCommandText("SELECT ?user WHERE { GRAPH ?g { ?user u:username ?username } } LIMIT 1");
        pss.setIri("g", GRAPH);
        pss.setLiteral("username", username);
        List<Map<String, String>> rows = actionLog.select(pss.toString());
        if (rows.isEmpty()) return null;
        String iri = rows.get(0).get("user");
        return iri == null ? null : iri.substring(PROFILE_NS.length() + "user/".length());
    }

    private static String sha256Hex(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }
}
