package com.conduit.app;

import com.conduit.app.engine.ActionLog;
import com.conduit.app.engine.CompletionBus;
import com.conduit.app.engine.FlowManager;
import org.junit.jupiter.api.BeforeEach;

/** Shared test fixtures for concept-level tests. */
public abstract class ConceptTestBase {

    protected ActionLog log;
    protected CompletionBus bus;
    protected FlowManager flow;

    @BeforeEach
    void setUpEngine() {
        log = new ActionLog();
        bus = new CompletionBus();
        flow = new FlowManager(log, bus);
    }
}
