package handlerTest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.HttpTaskServer;
import manager.*;
import model.Epic;
import model.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DurationTypeAdapter;
import util.LocalDateTimeTypeAdapter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class HttpEpicsHandlerTest {
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
    public void testAddEpic() throws IOException, InterruptedException {
        Epic epic = new Epic("Epic", "Description",
                Status.NEW, 1, Duration.ofMinutes(10), LocalDateTime.now());
        String epicJson = gson.toJson(epic);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/epics"))
                .POST(HttpRequest.BodyPublishers.ofString(epicJson))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());

        List<Epic> epics = taskManager.getAllTasks().stream()
                .filter(t -> t instanceof Epic)
                .map(t -> (Epic) t)
                .collect(Collectors.toList());
        assertEquals(1, epics.size());
        assertEquals("Epic", epics.get(0).getTaskName());
    }

    @Test
    public void testGetEpicById() throws IOException, InterruptedException {
        Epic epic = new Epic("Epic", "Description",
                Status.NEW, 1, Duration.ofMinutes(10), LocalDateTime.now());
        taskManager.addTask(epic);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/epics/1"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

        Epic returnedEpic = gson.fromJson(response.body(), Epic.class);
        assertEquals(epic.getTaskName(), returnedEpic.getTaskName());
    }

    @Test
    public void testUpdateEpic() throws IOException, InterruptedException {
        Epic epic = new Epic("Epic", "Description",
                Status.NEW, 1, Duration.ofMinutes(10), LocalDateTime.now());
        taskManager.addTask(epic);

        Epic updatedEpic = new Epic("Updated Epic", "Description",
                Status.IN_PROGRESS, 1, Duration.ofMinutes(20), LocalDateTime.now().plusHours(1));
        String epicJson = gson.toJson(updatedEpic);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/epics"))
                .POST(HttpRequest.BodyPublishers.ofString(epicJson))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());

        Epic returnedEpic = (Epic) taskManager.getTaskById(1);
        assertEquals("Updated Epic", returnedEpic.getTaskName());
        assertEquals(Status.IN_PROGRESS, returnedEpic.getStatus());
    }

    @Test
    public void testDeleteEpic() throws IOException, InterruptedException {
        Epic epic = new Epic("Epic", "Description",
                Status.NEW, 1, Duration.ofMinutes(10), LocalDateTime.now());
        taskManager.addTask(epic);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/epics/1"))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

        assertNull(taskManager.getTaskById(1));
    }
}
