package com.conduit.app.steps;

import com.conduit.app.api.RegisterRequest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.server.EmbeddedServer;

public class RegisterStepDefinitions {

    private static EmbeddedServer server;
    private static HttpClient client;

    private static synchronized void ensureServerRunning() {
        if (server == null || !server.isRunning()) {
            server = io.micronaut.context.ApplicationContext.run(EmbeddedServer.class);
            client = server.getApplicationContext().createBean(HttpClient.class, server.getURI());
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
        ensureServerRunning();
    }

    @Given("a user exists with email {string}")
    public void a_user_exists_with_email(String email) {
        ensureServerRunning();
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
