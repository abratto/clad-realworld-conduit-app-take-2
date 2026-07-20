package com.conduit.app.infrastructure;

import com.conduit.app.api.RegisterRequest;
import com.conduit.app.engine.ActionRecord;
import com.conduit.app.engine.FlowManager;
import com.conduit.app.engine.ResponseAssembler;
import com.conduit.app.engine.SyncDispatcher;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import reactor.core.publisher.Mono;

import java.util.Map;

@Tag(name = "web")
@Controller("/api")
public class RegisterController {

    private final FlowManager flowManager;
    private final SyncDispatcher syncDispatcher;
    private final ResponseAssembler responseAssembler;

    @Inject
    public RegisterController(FlowManager flowManager, SyncDispatcher syncDispatcher,
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
                io.micronaut.http.MutableHttpResponse<Object> result;
                if (resp.getStatus() == HttpStatus.CREATED || resp.getStatus() == HttpStatus.OK) {
                    result = io.micronaut.http.HttpResponse.status(resp.getStatus())
                            .body(responseAssembler.assemble(flowName, fields));
                } else {
                    String msg = fields.getOrDefault("message", "error");
                    String field;
                    if (msg.contains("already been taken")) {
                        field = msg.contains("email") ? "email" : "username";
                        msg = "has already been taken";
                    } else if (msg.contains("blank username")) {
                        field = "username"; msg = "can't be blank";
                    } else if (msg.contains("blank email")) {
                        field = "email"; msg = "can't be blank";
                    } else if (msg.contains("blank password")) {
                        field = "password"; msg = "can't be blank";
                    } else if (msg.contains("blank")) {
                        field = "username"; msg = "can't be blank";
                    } else {
                        field = "body";
                    }
                    result = io.micronaut.http.HttpResponse.status(resp.getStatus())
                            .body(java.util.Map.of("errors", java.util.Map.of(field, java.util.List.of(msg))));
                }
                String flowToken = resp.getHeaders().get("X-Flow-Token");
                if (flowToken != null) result.header("X-Flow-Token", flowToken);
                return result;
            }
            return resp;
        });
    }

    @Post(value = "/users", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    @Operation(summary = "Register a new user")
    @ApiResponse(responseCode = "201", description = "User registered successfully.")
    @ApiResponse(responseCode = "422", description = "Validation error.")
    @ApiResponse(responseCode = "409", description = "Conflict.")
    public Mono<HttpResponse<?>> register(@Body RegisterRequest body) {
        var user = body.user();
        ActionRecord root = flowManager.rootAction("/api/users", Map.of(
                "username", user.username() == null ? "" : user.username(),
                "email", user.email() == null ? "" : user.email(),
                "password", user.password() == null ? "" : user.password()
        ));
        return assemble("/api/users", syncDispatcher.awaitResponse(root.flowToken()));
    }
}
