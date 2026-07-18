package com.conduit.app.steps;

import io.micronaut.context.ApplicationContext;
import io.micronaut.http.client.HttpClient;
import io.micronaut.runtime.server.EmbeddedServer;

public final class ServerContext {

    private static EmbeddedServer server;
    private static HttpClient client;

    public static synchronized void ensureRunning() {
        if (server == null || !server.isRunning()) {
            server = ApplicationContext.run(EmbeddedServer.class);
            client = server.getApplicationContext().createBean(HttpClient.class, server.getURI());
        }
    }

    public static HttpClient client() {
        ensureRunning();
        return client;
    }

    public static EmbeddedServer server() {
        ensureRunning();
        return server;
    }
}
