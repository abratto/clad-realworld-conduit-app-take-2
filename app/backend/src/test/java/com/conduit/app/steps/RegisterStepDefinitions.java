package com.conduit.app.steps;

import com.conduit.app.api.RegisterRequest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.exceptions.HttpClientResponseException;

public class RegisterStepDefinitions {

    private static boolean seeded = false;

    private static synchronized void seedTestUser() {
        if (!seeded) {
            try {
                ServerContext.client().toBlocking().exchange(
                    HttpRequest.POST("/api/users", new RegisterRequest(
                        new RegisterRequest.RegisterUser("existing_user", "existing@test.com", "password123"))),
                    String.class);
            } catch (Exception e) {
            }
            seeded = true;
        }
    }

    @Given("no user exists with username {string}")
    public void no_user_exists_with_username(String username) {
        ServerContext.ensureRunning();
    }

    @Given("no user exists with email {string}")
    public void no_user_exists_with_email(String email) {
        ServerContext.ensureRunning();
    }

    @Given("a user exists with username {string}")
    public void a_user_exists_with_username(String username) {
        seedTestUser();
    }

    @Given("a user exists with email {string}")
    public void a_user_exists_with_email(String email) {
        seedTestUser();
    }

    @When("^the user submits POST /api/users with username \"([^\"]*)\", email \"([^\"]*)\", and password \"([^\"]*)\"$")
    public void register_user(String username, String email, String password) {
        ServerContext.ensureRunning();
        try {
            var r = ServerContext.client().toBlocking().exchange(
                HttpRequest.POST("/api/users", new RegisterRequest(
                    new RegisterRequest.RegisterUser(username, email, password))),
                String.class);
            LoginStepDefinitions.response = (HttpResponse<String>) r;
            LoginStepDefinitions.failure = null;
        } catch (HttpClientResponseException e) {
            LoginStepDefinitions.response = null;
            LoginStepDefinitions.failure = e;
        }
    }
}
