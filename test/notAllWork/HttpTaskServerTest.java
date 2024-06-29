/*
package main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import manager.InMemoryTaskManager;
import manager.TaskManager;
import model.Status;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DurationTypeAdapter;
import util.LocalDateTimeTypeAdapter;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HttpTaskServerTest {
    private HttpTaskServer server;
    private TaskManager taskManager;
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Duration.class, new DurationTypeAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
            .create();

    @BeforeEach
    public void setup() throws IOException {
        taskManager = new InMemoryTaskManager();
        server = new HttpTaskServer(taskManager);
        server.start();
    }

    @AfterEach
    public void tearDown() {
        server.stop();
    }

    @Test
    public void testAddTask() throws IOException {
        Task task = new Task("Test Task", "Test Description", Status.NEW, 1, Duration.ofMinutes(30), LocalDateTime.now());
        String taskJson = gson.toJson(task);

        URL url = new URL("http://localhost:8080/tasks");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.getOutputStream().write(taskJson.getBytes());

        assertEquals(201, connection.getResponseCode());

        List<Task> tasks = (List<Task>) taskManager.getAllTasks();
        assertEquals(1, tasks.size());
        assertEquals("Test Task", tasks.get(0).getTaskName());
    }

    @Test
    public void testGetAllTasks() throws IOException {
        Task task = new Task("Test Task", "Test Description", Status.NEW, 1, Duration.ofMinutes(30), LocalDateTime.now());
        taskManager.addTask(task);

        URL url = new URL("http://localhost:8080/tasks");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        assertEquals(200, connection.getResponseCode());

        Task[] tasks = gson.fromJson(new String(connection.getInputStream().readAllBytes()), Task[].class);
        assertEquals(1, tasks.length);
        assertEquals("Test Task", tasks[0].getTaskName());
    }

    @Test
    public void testDeleteTask() throws IOException {
        Task task = new Task("Test Task", "Test Description", Status.NEW, 1, Duration.ofMinutes(30), LocalDateTime.now());
        taskManager.addTask(task);

        URL url = new URL("http://localhost:8080/tasks/1");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");

        assertEquals(200, connection.getResponseCode());

        List<Task> tasks = (List<Task>) taskManager.getAllTasks();
        assertEquals(0, tasks.size());
    }

    @Test
    public void testGetTaskById() throws IOException {
        Task task = new Task("Test Task", "Test Description", Status.NEW, 1, Duration.ofMinutes(30), LocalDateTime.now());
        taskManager.addTask(task);

        URL url = new URL("http://localhost:8080/tasks/1");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        assertEquals(200, connection.getResponseCode());

        Task returnedTask = gson.fromJson(new String(connection.getInputStream().readAllBytes()), Task.class);
        assertNotNull(returnedTask);
        assertEquals("Test Task", returnedTask.getTaskName());
    }
}
*/
