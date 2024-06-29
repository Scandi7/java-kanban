package handlerTest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import main.HttpTaskServer;
import manager.*;
import model.Task;
import model.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DurationTypeAdapter;
import util.LocalDateTimeTypeAdapter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HttpPrioritizedTest {
    private HttpTaskServer taskServer;
    private TaskManager taskManager;
    private HttpClient client;
    private Gson gson;

    @BeforeEach
    public void setUp() throws IOException {
        taskManager = Managers.getDefaultTaskManager();
        gson = new GsonBuilder()
                .registerTypeAdapter(Duration.class, new DurationTypeAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .create();
        taskServer = new HttpTaskServer(8080, taskManager);
        client = HttpClient.newHttpClient();
        taskServer.start();
    }

    @AfterEach
    public void tearDown() {
        taskServer.stop();
    }

    @Test
    public void testGetPrioritizedTasks() throws IOException, InterruptedException {
        Task task1 = new Task("Task 1", "Description",
                Status.NEW, 1, Duration.ofMinutes(10), LocalDateTime.now());
        Task task2 = new Task("Task 2", "Description",
                Status.NEW, 2, Duration.ofMinutes(15), LocalDateTime.now().plusMinutes(15));
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/prioritized"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

        Type taskListType = new TypeToken<List<Task>>(){}.getType();
        List<Task> prioritizedTasks = gson.fromJson(response.body(), taskListType);

        assertEquals(2, prioritizedTasks.size());
        assertEquals(task1.getTaskName(), prioritizedTasks.get(0).getTaskName());
        assertEquals(task2.getTaskName(), prioritizedTasks.get(1).getTaskName());
    }
}
