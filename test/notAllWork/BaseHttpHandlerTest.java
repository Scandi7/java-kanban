/*
package handlerTest;

import main.HttpTaskServer;
import manager.InMemoryTaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public abstract class BaseHttpHandlerTest {
    private static final int PORT = 8080;
    protected HttpTaskServer server;

    @BeforeEach
    public void startServer() throws IOException {
        server = new HttpTaskServer(PORT, new InMemoryTaskManager());
        server.start();
    }

    @AfterEach
    public void stopServer() {
        server.stop();
    }
}
*/
