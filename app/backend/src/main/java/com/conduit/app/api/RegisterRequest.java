package com.conduit.app.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

@Introspected
@Schema(name = "RegisterRequest", description = "Registration request payload.")
public record RegisterRequest(
    @Schema(description = "Inner user object containing registration fields.")
    @JsonProperty("user") RegisterUser user
) {
    @Introspected
    @Schema(name = "RegisterUser")
    public record RegisterUser(
        @Schema(description = "Desired username.", example = "jdoe") String username,
        @Schema(description = "Email address.", example = "jdoe@test.com") String email,
        @Schema(description = "Password.", example = "secret123") String password
    ) {}
}
