package handlerTest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class HttpTasksTest {
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
    public void testAddTask() throws IOException, InterruptedException {
        Task task = new Task("Task", "Description",
                Status.NEW, 1, Duration.ofMinutes(10), LocalDateTime.now());
        String taskJson = gson.toJson(task);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks"))
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());

        List<Task> tasks = taskManager.getAllTasks().stream()
                .filter(t -> !(t instanceof model.Epic) && !(t instanceof model.Subtask))
                .collect(Collectors.toList());
        assertEquals(1, tasks.size());
        assertEquals("Task", tasks.get(0).getTaskName());
    }

    @Test
    public void testGetTaskById() throws IOException, InterruptedException {
        Task task = new Task("Task", "Description",
                Status.NEW, 1, Duration.ofMinutes(10), LocalDateTime.now());
        taskManager.addTask(task);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/1"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

        Task returnedTask = gson.fromJson(response.body(), Task.class);
        assertEquals(task.getTaskName(), returnedTask.getTaskName());
    }

    @Test
    public void testUpdateTask() throws IOException, InterruptedException {
        Task task = new Task("Task", "Description",
                Status.NEW, 1, Duration.ofMinutes(10), LocalDateTime.now());
        taskManager.addTask(task);

        Task updatedTask = new Task("Updated Task", "Description",
                Status.IN_PROGRESS, 1, Duration.ofMinutes(20), LocalDateTime.now().plusHours(1));
        String taskJson = gson.toJson(updatedTask);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks"))
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());

        Task returnedTask = taskManager.getTaskById(1);
        assertEquals("Updated Task", returnedTask.getTaskName());
        assertEquals(Status.IN_PROGRESS, returnedTask.getStatus());
    }

    @Test
    public void testDeleteTask() throws IOException, InterruptedException {
        Task task = new Task("Task", "Description",
                Status.NEW, 1, Duration.ofMinutes(10), LocalDateTime.now());
        taskManager.addTask(task);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/1"))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

        assertNull(taskManager.getTaskById(1));
    }
}
