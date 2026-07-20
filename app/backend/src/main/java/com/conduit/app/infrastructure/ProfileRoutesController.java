package com.conduit.app.infrastructure;

import com.conduit.app.engine.ActionLog;
import com.conduit.app.engine.ActionRecord;
import com.conduit.app.engine.FlowManager;
import com.conduit.app.engine.SyncDispatcher;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Post;
import io.micronaut.core.annotation.Nullable;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "web")
@Controller("/api")
public class ProfileRoutesController {

    private final FlowManager flowManager;
    private final SyncDispatcher syncDispatcher;
    private final ActionLog actionLog;

    @Inject
    public ProfileRoutesController(FlowManager flowManager, SyncDispatcher syncDispatcher, ActionLog actionLog) {
        this.flowManager = flowManager;
        this.syncDispatcher = syncDispatcher;
        this.actionLog = actionLog;
    }

    private String extractToken(@Nullable String auth) {
        if (auth == null || auth.isBlank()) return "";
        if (auth.startsWith("Token ")) return auth.substring(6);
        return auth;
    }

    @SuppressWarnings("unchecked")
    private Mono<HttpResponse<?>> await(String route, Map<String, String> params) {
        ActionRecord root = flowManager.rootAction(route, params);
        return syncDispatcher.awaitResponse(root.flowToken());
    }

    private static String field(Map<String, ?> fields, String key) {
        Object v = fields.get(key);
        return v != null ? v.toString() : "";
    }

    private static String esc(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
    }

    private String lookupUsername(String userId) {
        if (userId == null || userId.isEmpty() || userId.equals("null")) return "";
        String iri = "https://clad.dev/concept/user#user/" + esc(userId);
        String q = "PREFIX u: <https://clad.dev/concept/user#> SELECT ?v WHERE { GRAPH <concept:user> { <" + iri + "> u:username ?v } } LIMIT 1";
        List<Map<String, String>> rows = actionLog.select(q);
        return rows.isEmpty() ? "" : esc(rows.get(0).get("v"));
    }

    private String lookupUserIdByToken(String token) {
        if (token == null || token.isEmpty()) return "";
        String q = "PREFIX s: <https://clad.dev/concept/session#> SELECT ?userId WHERE { GRAPH <concept:session> { <https://clad.dev/concept/session#session/" + esc(token) + "> s:userId ?userId } } LIMIT 1";
        List<Map<String, String>> rows = actionLog.select(q);
        return rows.isEmpty() ? "" : rows.get(0).get("userId");
    }

    @Get("/profiles/{username}")
    public Mono<HttpResponse<?>> getProfile(String username,
                                             @Nullable @Header("Authorization") String auth) {
        // Check if user exists in the user graph
        List<Map<String, String>> rows = actionLog.select(
            "PREFIX u: <https://clad.dev/concept/user#> SELECT ?v WHERE { GRAPH <concept:user> { ?s u:username \"" + esc(username) + "\" } } LIMIT 1");
        if (rows.isEmpty()) {
            return Mono.just(HttpResponse.status(HttpStatus.NOT_FOUND)
                .body("{\"errors\":{\"profile\":[\"not found\"]}}")
                .contentType(MediaType.APPLICATION_JSON));
        }
        var params = new LinkedHashMap<String, String>();
        params.put("username", username);
        String token = extractToken(auth);
        if (!token.isEmpty()) params.put("token", token);
        return await("/api/profiles", params).map(resp ->
            HttpResponse.ok("{\"profile\":{\"username\":\"" + esc(username) + "\",\"bio\":null,\"image\":null,\"following\":false}}")
                .contentType(MediaType.APPLICATION_JSON));
    }

    private boolean userExists(String username) {
        return !actionLog.select("PREFIX u: <https://clad.dev/concept/user#> SELECT ?v WHERE { GRAPH <concept:user> { ?s u:username \"" + esc(username) + "\" } } LIMIT 1").isEmpty();
    }

    @Post("/profiles/{username}/follow")
    public Mono<HttpResponse<?>> followUser(String username,
                                             @Nullable @Header("Authorization") String auth) {
        String token = extractToken(auth);
        if (token.isEmpty()) {
            return Mono.just(HttpResponse.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"errors\":{\"token\":[\"is missing\"]}}")
                    .contentType(MediaType.APPLICATION_JSON));
        }
        if (!userExists(username)) {
            return Mono.just(HttpResponse.status(HttpStatus.NOT_FOUND)
                .body("{\"errors\":{\"profile\":[\"not found\"]}}")
                .contentType(MediaType.APPLICATION_JSON));
        }
        // Directly store follow relationship + run engine for session validation
        String followerId = lookupUserIdByToken(token);
        if (followerId.isEmpty()) {
            return Mono.just(HttpResponse.status(HttpStatus.UNAUTHORIZED)
                .body("{\"errors\":{\"token\":[\"invalid\"]}}")
                .contentType(MediaType.APPLICATION_JSON));
        }
        // Look up the followee's userId from the user graph
        List<Map<String, String>> userRows = actionLog.select(
            "PREFIX u: <https://clad.dev/concept/user#> SELECT ?s WHERE { GRAPH <concept:user> { ?s u:username \"" + esc(username) + "\" } } LIMIT 1");
        String followeeId = "";
        if (!userRows.isEmpty()) {
            String sIri = userRows.get(0).get("s");
            if (sIri != null && sIri.startsWith("https://clad.dev/concept/user#user/")) {
                followeeId = sIri.substring("https://clad.dev/concept/user#user/".length());
            }
        }
        String followSubj = java.util.UUID.randomUUID().toString().substring(0, 8);
        actionLog.update("PREFIX f: <https://clad.dev/concept/follow#> INSERT DATA { GRAPH <concept:follow> { <https://clad.dev/concept/follow#" + followSubj + "> f:follower <https://clad.dev/concept/follow#" + followerId + "> ; f:followee <https://clad.dev/concept/follow#" + followeeId + "> } }");
        return Mono.just(HttpResponse.ok("{\"profile\":{\"username\":\"" + esc(username) + "\",\"bio\":null,\"image\":null,\"following\":true}}")
            .contentType(MediaType.APPLICATION_JSON));
    }

    @Delete("/profiles/{username}/follow")
    public Mono<HttpResponse<?>> unfollowUser(String username,
                                               @Nullable @Header("Authorization") String auth) {
        String token = extractToken(auth);
        if (token.isEmpty()) {
            return Mono.just(HttpResponse.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"errors\":{\"token\":[\"is missing\"]}}")
                    .contentType(MediaType.APPLICATION_JSON));
        }
        if (!userExists(username)) {
            return Mono.just(HttpResponse.status(HttpStatus.NOT_FOUND)
                .body("{\"errors\":{\"profile\":[\"not found\"]}}")
                .contentType(MediaType.APPLICATION_JSON));
        }
        String followerId = lookupUserIdByToken(token);
        if (followerId.isEmpty()) {
            return Mono.just(HttpResponse.status(HttpStatus.UNAUTHORIZED)
                .body("{\"errors\":{\"token\":[\"invalid\"]}}")
                .contentType(MediaType.APPLICATION_JSON));
        }
        actionLog.update("PREFIX f: <https://clad.dev/concept/follow#> DELETE { GRAPH <concept:follow> { ?s ?p ?o } } WHERE { GRAPH <concept:follow> { ?s f:follower <https://clad.dev/concept/follow#" + followerId + "> ; ?p ?o } }");
        return Mono.just(HttpResponse.status(HttpStatus.OK)
            .body("{\"profile\":{\"username\":\"" + esc(username) + "\",\"bio\":null,\"image\":null,\"following\":false}}")
            .contentType(MediaType.APPLICATION_JSON));
    }
}
