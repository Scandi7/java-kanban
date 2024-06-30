package handlerTest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.HttpTaskServer;
import manager.*;
import model.Epic;
import model.Status;
import model.Subtask;
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

public class HttpSubtasksTest {
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
    public void testAddSubtask() throws IOException, InterruptedException {
        Epic epic = new Epic("Epic", "Description",
                Status.NEW, 1, Duration.ofMinutes(10), LocalDateTime.now());
        taskManager.addTask(epic);

        Subtask subtask = new Subtask("Subtask", "Description",
                Status.NEW, 2, epic, Duration.ofMinutes(10), LocalDateTime.now());
        String subtaskJson = gson.toJson(subtask);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/subtasks"))
                .POST(HttpRequest.BodyPublishers.ofString(subtaskJson))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());

        List<Subtask> subtasks = taskManager.getAllTasks().stream()
                .filter(t -> t instanceof Subtask)
                .map(t -> (Subtask) t)
                .collect(Collectors.toList());
        assertEquals(1, subtasks.size());
        assertEquals("Subtask", subtasks.get(0).getTaskName());
    }

    @Test
    public void testGetSubtaskById() throws IOException, InterruptedException {
        Epic epic = new Epic("Epic", "Description",
                Status.NEW, 1, Duration.ofMinutes(10), LocalDateTime.now());
        taskManager.addTask(epic);

        Subtask subtask = new Subtask("Subtask", "Description",
                Status.NEW, 2, epic, Duration.ofMinutes(10), LocalDateTime.now());
        taskManager.addTask(subtask);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/subtasks/2"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

        Subtask returnedSubtask = gson.fromJson(response.body(), Subtask.class);
        assertEquals(subtask.getTaskName(), returnedSubtask.getTaskName());
    }

    @Test
    public void testUpdateSubtask() throws IOException, InterruptedException {
        Epic epic = new Epic("Epic", "Description",
                Status.NEW, 1, Duration.ofMinutes(10), LocalDateTime.now());
        taskManager.addTask(epic);

        Subtask subtask = new Subtask("Subtask", "Description",
                Status.NEW, 2, epic, Duration.ofMinutes(10), LocalDateTime.now());
        taskManager.addTask(subtask);

        Subtask updatedSubtask = new Subtask("Updated Subtask", "Description",
                Status.IN_PROGRESS, 2, epic, Duration.ofMinutes(20), LocalDateTime.now().plusHours(1));
        String subtaskJson = gson.toJson(updatedSubtask);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/subtasks"))
                .POST(HttpRequest.BodyPublishers.ofString(subtaskJson))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());

        Subtask returnedSubtask = (Subtask) taskManager.getTaskById(2);
        assertEquals("Updated Subtask", returnedSubtask.getTaskName());
        assertEquals(Status.IN_PROGRESS, returnedSubtask.getStatus());
    }

    @Test
    public void testDeleteSubtask() throws IOException, InterruptedException {
        Epic epic = new Epic("Epic", "Description",
                Status.NEW, 1, Duration.ofMinutes(10), LocalDateTime.now());
        taskManager.addTask(epic);

        Subtask subtask = new Subtask("Subtask", "Description",
                Status.NEW, 2, epic, Duration.ofMinutes(10), LocalDateTime.now());
        taskManager.addTask(subtask);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/subtasks/2"))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

        assertNull(taskManager.getTaskById(2));
    }
}
