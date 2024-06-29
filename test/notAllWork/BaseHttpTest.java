/*
package handlerTest;

import adapter.DurationAdapter;
import adapter.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.HttpTaskServer;
import manager.InMemoryTaskManager;
import manager.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;

public abstract class BaseHttpTest {
    protected HttpTaskServer server;
    protected TaskManager taskManager;
    protected Gson gson;
    protected final int PORT = 8080;

    @BeforeEach
    public void setUp() throws IOException {
        taskManager = new InMemoryTaskManager();
        gson = new GsonBuilder()
                .registerTypeAdapter(Duration.class, new DurationAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        server = new HttpTaskServer(PORT, taskManager);
        server.start();
    }

    @AfterEach
    public void tearDown() {
        server.stop();
    }

    protected HttpURLConnection createConnection(String path, String method) throws IOException {
        URL url = new URL("http://localhost:" + PORT + path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        connection.setDoOutput(true);
        return connection;
    }
}
*/
