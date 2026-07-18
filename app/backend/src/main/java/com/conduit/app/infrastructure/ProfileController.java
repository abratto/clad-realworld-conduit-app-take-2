package com.conduit.app.infrastructure;

import com.conduit.app.engine.ActionRecord;
import com.conduit.app.engine.FlowManager;
import com.conduit.app.engine.ResponseAssembler;
import com.conduit.app.engine.SyncDispatcher;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.Post;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.Map;

@Tag(name = "web")
@Controller("/api")
public class ProfileController {

    private final FlowManager flowManager;
    private final SyncDispatcher syncDispatcher;
    private final ResponseAssembler responseAssembler;

    @Inject
    public ProfileController(FlowManager flowManager, SyncDispatcher syncDispatcher,
                             ResponseAssembler responseAssembler) {
        this.flowManager = flowManager;
        this.syncDispatcher = syncDispatcher;
        this.responseAssembler = responseAssembler;
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

    private String extractToken(String authHeader) {
        if (authHeader == null || authHeader.isBlank()) return "";
        if (authHeader.startsWith("Token ")) return authHeader.substring(6);
        return authHeader;
    }

    @Get("/user")
    public Mono<HttpResponse<?>> getProfile(@Header("Authorization") String auth) {
        String token = extractToken(auth);
        if (token.isEmpty()) {
            return Mono.just(HttpResponse.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("errors", Map.of("token", "is missing"))));
        }
        ActionRecord root = flowManager.rootAction("profile", Map.of("token", token));
        return assemble("profile", syncDispatcher.awaitResponse(root.flowToken()));
    }
}
