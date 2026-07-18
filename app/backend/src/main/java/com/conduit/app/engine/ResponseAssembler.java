package com.conduit.app.engine;

import com.conduit.app.api.LoginFailureResponse;
import com.conduit.app.api.LoginSuccessResponse;
import com.conduit.app.api.RegisterSuccessResponse;
import jakarta.inject.Singleton;

import java.util.Map;

@Singleton
public class ResponseAssembler {

    public Object assemble(String flowName, Map<String, String> fields) {
        return switch (flowName) {
            case "login" -> new LoginSuccessResponse(fields.get("sessionToken"));
            case "/api/users" -> new RegisterSuccessResponse(
                    new RegisterSuccessResponse.RegisteredUser(
                            fields.get("email"),
                            fields.get("token"),
                            fields.get("username"),
                            fields.get("bio"),
                            fields.get("image")));
            default -> fields;
        };
    }

    public LoginFailureResponse toError(Map<String, String> fields) {
        String msg = fields.get("message");
        return new LoginFailureResponse(
                msg != null ? msg : "An error occurred");
    }
}
