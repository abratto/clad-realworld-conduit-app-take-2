package com.conduit.app.api;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

/** JSON body for {@code POST /login}. */
@Introspected
@Schema(name = "LoginRequest", description = "Login request payload accepted by the Web bootstrap boundary.")
public record LoginRequest(
	@Schema(description = "Username to authenticate.", example = "ada")
	String username,
	@Schema(description = "Email to authenticate.", example = "ada@test.com")
	String email,
	@Schema(description = "Plain-text password for the demo login flow.", example = "correct-horse-battery-staple")
	String password) {

	public LoginRequest(String username, String password) {
		this(username, null, password);
	}
}
