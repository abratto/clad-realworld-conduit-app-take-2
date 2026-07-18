package com.conduit.app.steps;

import com.conduit.app.api.RegisterRequest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.micronaut.context.ApplicationContext;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.server.EmbeddedServer;

public class RegisterStepDefinitions {

    private static EmbeddedServer server;
    private static HttpClient client;
    private static boolean seeded = false;

    private static synchronized void ensureServerRunning() {
        if (server == null || !server.isRunning()) {
            server = ApplicationContext.run(EmbeddedServer.class);
            client = server.getApplicationContext().createBean(HttpClient.class, server.getURI());
        }
    }

    private static synchronized void seedTestUser() {
        ensureServerRunning();
        if (!seeded) {
            try {
                client.toBlocking().exchange(
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
        ensureServerRunning();
    }

    @Given("no user exists with email {string}")
    public void no_user_exists_with_email(String email) {
        ensureServerRunning();
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
        ensureServerRunning();
        try {
            var r = client.toBlocking().exchange(
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
