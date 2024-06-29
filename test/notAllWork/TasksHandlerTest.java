/*
package handlerTest;

import model.Task;
import model.Status;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TasksHandlerTest extends BaseHttpTest {

    @Test
    public void testAddTask() throws IOException {
        Task task = new Task("Task 1", "Description 1", Status.NEW, 1, Duration.ofMinutes(60), LocalDateTime.now());
        String json = gson.toJson(task);

        HttpURLConnection connection = createConnection("/tasks", "POST");
        connection.getOutputStream().write(json.getBytes(StandardCharsets.UTF_8));

        assertEquals(HttpURLConnection.HTTP_CREATED, connection.getResponseCode());
        assertNotNull(taskManager.getTaskById(1));
    }

    @Test
    public void testGetTasks() throws IOException {
        taskManager.addTask(new Task("Task 1", "Description 1", Status.NEW, 1, Duration.ofMinutes(60), LocalDateTime.now()));

        HttpURLConnection connection = createConnection("/tasks", "GET");
        assertEquals(HttpURLConnection.HTTP_OK, connection.getResponseCode());

        String response = new String(connection.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        Task[] tasks = gson.fromJson(response, Task[].class);

        assertEquals(1, tasks.length);
        assertEquals("Task 1", tasks[0].getTaskName());
    }

    @Test
    public void testUpdateTask() throws IOException {
        Task task = new Task("Task 1", "Description 1", Status.NEW, 1, Duration.ofMinutes(60), LocalDateTime.now());
        taskManager.addTask(task);

        task.setTaskName("Updated Task 1");
        String json = gson.toJson(task);

        HttpURLConnection connection = createConnection("/tasks", "POST");
        connection.getOutputStream().write(json.getBytes(StandardCharsets.UTF_8));

        assertEquals(HttpURLConnection.HTTP_CREATED, connection.getResponseCode());
        assertEquals("Updated Task 1", taskManager.getTaskById(1).getTaskName());
    }

    @Test
    public void testDeleteTask() throws IOException {
        Task task = new Task("Task 1", "Description 1", Status.NEW, 1, Duration.ofMinutes(60), LocalDateTime.now());
        taskManager.addTask(task);

        HttpURLConnection connection = createConnection("/tasks/1", "DELETE");
        assertEquals(HttpURLConnection.HTTP_OK, connection.getResponseCode());

        assertEquals(0, taskManager.getAllTasks().size());
    }
}
*/
