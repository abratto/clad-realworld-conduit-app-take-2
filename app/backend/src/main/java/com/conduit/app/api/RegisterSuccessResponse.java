package com.conduit.app.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

@Introspected
@Schema(name = "RegisterSuccessResponse", description = "Successful registration response.")
public record RegisterSuccessResponse(
    @Schema(description = "Inner user object containing profile and token.")
    @JsonProperty("user") RegisteredUser user
) {
    @Introspected
    @Schema(name = "RegisteredUser")
    public record RegisteredUser(
        @Schema(description = "Email address.", example = "jdoe@test.com") String email,
        @Schema(description = "JWT token.", example = "eyJ...") String token,
        @Schema(description = "Username.", example = "jdoe") String username,
        @Schema(description = "User bio.", nullable = true) String bio,
        @Schema(description = "User image URL.", nullable = true) String image
    ) {}
}
