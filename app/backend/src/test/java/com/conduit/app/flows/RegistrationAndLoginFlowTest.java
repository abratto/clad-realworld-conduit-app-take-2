package com.conduit.app.flows;

import com.conduit.app.api.RegisterRequest;
import io.micronaut.context.ApplicationContext;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.server.EmbeddedServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationAndLoginFlowTest {

    private static EmbeddedServer server;
    private static HttpClient client;

    @BeforeAll
    static void setup() {
        server = ApplicationContext.run(EmbeddedServer.class);
        client = server.getApplicationContext().createBean(HttpClient.class, server.getURI());
    }

    @AfterAll
    static void cleanup() {
        if (client != null) client.close();
        if (server != null) server.close();
    }

    @Test
    void registrationWorks() {
        String username = "reg_" + System.currentTimeMillis();
        try {
            String body = client.toBlocking().retrieve(
                HttpRequest.POST("/api/users", new RegisterRequest(
                    new RegisterRequest.RegisterUser(username, username + "@test.com", "pass"))),
                String.class);
            System.out.println("REG RESPONSE: " + body);
            assertTrue(body.contains("token"));
        } catch (HttpClientResponseException e) {
            String errBody = e.getResponse().getBody(String.class).orElse("no body");
            System.out.println("REG ERROR: " + e.getStatus() + " " + errBody);
            fail("Registration failed: " + errBody);
        }
    }
}
