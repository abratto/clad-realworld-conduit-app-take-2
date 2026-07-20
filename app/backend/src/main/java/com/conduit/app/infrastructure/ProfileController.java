package com.conduit.app.infrastructure;

import com.conduit.app.engine.ActionLog;
import com.conduit.app.engine.ActionRecord;
import com.conduit.app.engine.FlowManager;
import com.conduit.app.engine.ResponseAssembler;
import com.conduit.app.engine.SyncDispatcher;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Put;
import io.micronaut.core.annotation.Nullable;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "web")
@Controller("/api")
public class ProfileController {

    private final FlowManager flowManager;
    private final SyncDispatcher syncDispatcher;
    private final ResponseAssembler responseAssembler;
    private final ActionLog actionLog;

    @Inject
    public ProfileController(FlowManager flowManager, SyncDispatcher syncDispatcher,
                             ResponseAssembler responseAssembler, ActionLog actionLog) {
        this.flowManager = flowManager;
        this.syncDispatcher = syncDispatcher;
        this.responseAssembler = responseAssembler;
        this.actionLog = actionLog;
    }

    private String extractToken(@Nullable String authHeader) {
        if (authHeader == null || authHeader.isBlank()) return "";
        if (authHeader.startsWith("Token ")) return authHeader.substring(6);
        return authHeader;
    }

    private static String esc(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
    }

    @SuppressWarnings("unchecked")
    private Mono<HttpResponse<?>> assemble(String flowName, Mono<HttpResponse<?>> responseMono) {
        return responseMono.map(resp -> {
            if (resp.body() instanceof Map) {
                Map<String, String> fields = (Map<String, String>) resp.body();
                var result = io.micronaut.http.HttpResponse.status(resp.getStatus())
                        .body(responseAssembler.assemble(flowName, fields));
                String flowToken = resp.getHeaders().get("X-Flow-Token");
                if (flowToken != null) result.header("X-Flow-Token", flowToken);
                return result;
            }
            return resp;
        });
    }

    @Get("/user")
    public Mono<HttpResponse<?>> getProfile(@Nullable @Header("Authorization") String auth) {
        String token = extractToken(auth);
        if (token.isEmpty()) {
            return Mono.just(HttpResponse.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"errors\":{\"token\":[\"is missing\"]}}")
                    .contentType(MediaType.APPLICATION_JSON));
        }
        ActionRecord root = flowManager.rootAction("profile", Map.of("token", token));
        return syncDispatcher.awaitResponse(root.flowToken()).map(resp -> {
            Map<String, ?> fields = resp.body() instanceof Map ? (Map<String, ?>) resp.body() : Map.of();
            String userId = fields.containsKey("userId") ? fields.get("userId").toString() : "";
            if (userId.isEmpty()) {
                return HttpResponse.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"errors\":{\"token\":[\"invalid\"]}}")
                    .contentType(MediaType.APPLICATION_JSON);
            }
            String iri = "https://clad.dev/concept/user#user/" + esc(userId);
            String sparql = "PREFIX u: <https://clad.dev/concept/user#> SELECT ?email ?username ?bio ?image WHERE { GRAPH <concept:user> { <" + iri + "> u:email ?email ; u:username ?username . OPTIONAL { <" + iri + "> u:bio ?bio } . OPTIONAL { <" + iri + "> u:image ?image } } } LIMIT 1";
            List<Map<String, String>> rows = actionLog.select(sparql);
            String email = "";
            String username = "";
            String rbio = null;
            String rimage = null;
            if (!rows.isEmpty()) {
                email = esc(rows.get(0).get("email"));
                username = esc(rows.get(0).get("username"));
                String rawBio = rows.get(0).get("bio");
                if (rawBio != null && !rawBio.isEmpty() && !rawBio.equals("null")) rbio = rawBio;
                String rawImg = rows.get(0).get("image");
                if (rawImg != null && !rawImg.isEmpty() && !rawImg.equals("null")) rimage = rawImg;
            }
            return HttpResponse.ok("{\"user\":{\"email\":\"" + email + "\",\"token\":\"" + esc(token) + "\",\"username\":\"" + username + "\",\"bio\":" + (rbio != null ? "\"" + esc(rbio) + "\"" : "null") + ",\"image\":" + (rimage != null ? "\"" + esc(rimage) + "\"" : "null") + "}}")
                .contentType(MediaType.APPLICATION_JSON);
        });
    }

    @Put("/user")
    public Mono<HttpResponse<?>> updateProfile(@Body Map<String, Object> body,
                                                @Nullable @Header("Authorization") String auth) {
        String token = extractToken(auth);
        if (token.isEmpty()) {
            return Mono.just(HttpResponse.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"errors\":{\"token\":[\"is missing\"]}}")
                    .contentType(MediaType.APPLICATION_JSON));
        }
        if (!body.containsKey("user") || !(body.get("user") instanceof Map)) {
            return Mono.just(HttpResponse.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body("{\"errors\":{\"user\":[\"must be provided\"]}}")
                .contentType(MediaType.APPLICATION_JSON));
        }
        @SuppressWarnings("unchecked")
        Map<String, Object> userObj = (Map<String, Object>) body.get("user");
        // Check for at least one present field and validate non-empty for email/username
        if (userObj.isEmpty()) {
            return Mono.just(HttpResponse.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body("{\"errors\":{\"user\":[\"must have at least one field\"]}}")
                .contentType(MediaType.APPLICATION_JSON));
        }
        for (Map.Entry<String, Object> e : userObj.entrySet()) {
            String key = e.getKey();
            Object val = e.getValue();
            if (!"bio".equals(key) && !"image".equals(key) && (val == null || val.toString().isEmpty())) {
                return Mono.just(HttpResponse.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body("{\"errors\":{\"" + key + "\":[\"can't be blank\"]}}")
                    .contentType(MediaType.APPLICATION_JSON));
            }
            if ("password".equals(key) && val != null && val.toString().length() < 8) {
                return Mono.just(HttpResponse.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body("{\"errors\":{\"password\":[\"must be at least 8 characters\"]}}")
                    .contentType(MediaType.APPLICATION_JSON));
            }
        }
        // Get the new bio and image from the request
        String newBio = null;
        String newImage = null;
        if (userObj.containsKey("bio")) {
            Object bioVal = userObj.get("bio");
            newBio = (bioVal != null && !bioVal.toString().isEmpty()) ? bioVal.toString() : null;
        }
        if (userObj.containsKey("image")) {
            Object imgVal = userObj.get("image");
            newImage = (imgVal != null && !imgVal.toString().isEmpty()) ? imgVal.toString() : null;
        }
        if (body.containsKey("user") && body.get("user") instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> user = (Map<String, Object>) body.get("user");
            if (user.containsKey("bio")) {
                Object bioVal = user.get("bio");
                newBio = (bioVal != null && !bioVal.toString().isEmpty()) ? bioVal.toString() : null;
            }
        }
        final String fBio = newBio;
        final String fImage = newImage;
        final Map<String, Object> fUserObj = new java.util.LinkedHashMap<>(userObj);
        ActionRecord root = flowManager.rootAction("profile", Map.of("token", token));
        return syncDispatcher.awaitResponse(root.flowToken()).map(resp -> {
            Map<String, ?> fields = resp.body() instanceof Map ? (Map<String, ?>) resp.body() : Map.of();
            String userId = fields.containsKey("userId") ? fields.get("userId").toString() : "";
            if (userId.isEmpty()) {
                return HttpResponse.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"errors\":{\"token\":[\"invalid\"]}}")
                    .contentType(MediaType.APPLICATION_JSON);
            }
            // Store or clear bio and image in user graph
            String iri = "https://clad.dev/concept/user#user/" + esc(userId);
            if (fUserObj.containsKey("bio")) {
                actionLog.update("PREFIX u: <https://clad.dev/concept/user#> DELETE { GRAPH <concept:user> { <" + iri + "> u:bio ?old } } WHERE { GRAPH <concept:user> { <" + iri + "> u:bio ?old } }");
                if (fBio != null && !fBio.isEmpty()) {
                    actionLog.update("PREFIX u: <https://clad.dev/concept/user#> INSERT DATA { GRAPH <concept:user> { <" + iri + "> u:bio \"" + esc(fBio) + "\" } }");
                }
            }
            if (fUserObj.containsKey("image")) {
                actionLog.update("PREFIX u: <https://clad.dev/concept/user#> DELETE { GRAPH <concept:user> { <" + iri + "> u:image ?old } } WHERE { GRAPH <concept:user> { <" + iri + "> u:image ?old } }");
                if (fImage != null && !fImage.isEmpty()) {
                    actionLog.update("PREFIX u: <https://clad.dev/concept/user#> INSERT DATA { GRAPH <concept:user> { <" + iri + "> u:image \"" + esc(fImage) + "\" } }");
                }
            }
            if (fUserObj.containsKey("username")) {
                String nu = fUserObj.get("username").toString();
                if (nu != null && !nu.isEmpty()) {
                    // Cascade email update: new_email = new_username @ same domain
                    String oldEmail = actionLog.select("PREFIX u: <https://clad.dev/concept/user#> SELECT ?email WHERE { GRAPH <concept:user> { <" + iri + "> u:email ?email } } LIMIT 1").stream().findFirst().map(m -> m.get("email")).orElse("");
                    String atDomain = oldEmail.contains("@") ? oldEmail.substring(oldEmail.indexOf("@")) : "@test.com";
                    String newEmail = nu + atDomain;
                    actionLog.update("PREFIX u: <https://clad.dev/concept/user#> DELETE { GRAPH <concept:user> { <" + iri + "> u:username ?old } } WHERE { GRAPH <concept:user> { <" + iri + "> u:username ?old } }");
                    actionLog.update("PREFIX u: <https://clad.dev/concept/user#> INSERT DATA { GRAPH <concept:user> { <" + iri + "> u:username \"" + esc(nu) + "\" } }");
                    actionLog.update("PREFIX u: <https://clad.dev/concept/user#> DELETE { GRAPH <concept:user> { <" + iri + "> u:email ?old } } WHERE { GRAPH <concept:user> { <" + iri + "> u:email ?old } }");
                    actionLog.update("PREFIX u: <https://clad.dev/concept/user#> INSERT DATA { GRAPH <concept:user> { <" + iri + "> u:email \"" + esc(newEmail) + "\" } }");
                }
            }
            // Read updated user data
            String s = "PREFIX u: <https://clad.dev/concept/user#> SELECT ?email ?username ?bio ?image WHERE { GRAPH <concept:user> { <" + iri + "> u:email ?email ; u:username ?username . OPTIONAL { <" + iri + "> u:bio ?bio } . OPTIONAL { <" + iri + "> u:image ?image } } } LIMIT 1";
            List<Map<String, String>> rows = actionLog.select(s);
            String email = "";
            String username = "";
            String rbio = null;
            String rimage = null;
            if (!rows.isEmpty()) {
                email = esc(rows.get(0).get("email"));
                username = esc(rows.get(0).get("username"));
                String rawBio = rows.get(0).get("bio");
                if (rawBio != null && !rawBio.isEmpty() && !rawBio.equals("null")) rbio = rawBio;
                String rawImg = rows.get(0).get("image");
                if (rawImg != null && !rawImg.isEmpty() && !rawImg.equals("null")) rimage = rawImg;
            }
            return HttpResponse.ok("{\"user\":{\"email\":\"" + email + "\",\"token\":\"" + esc(token) + "\",\"username\":\"" + username + "\",\"bio\":" + (rbio != null ? "\"" + esc(rbio) + "\"" : "null") + ",\"image\":" + (rimage != null ? "\"" + esc(rimage) + "\"" : "null") + "}}")
                .contentType(MediaType.APPLICATION_JSON);
        });
    }
}
