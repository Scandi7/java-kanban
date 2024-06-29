/*
package handlerTest;


import adapter.DurationAdapter;
import adapter.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Status;
import model.Task;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HistoryHandlerTest extends BaseHttpHandlerTest {
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Duration.class, new DurationAdapter())
            .registerTypeAdapter(LocalDateTimeAdapter.class, new LocalDateTimeAdapter())
            .create();
    @Test
    public void testGetHistory() throws IOException {
        URL url = new URL("http://localhost:8080/tasks");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json; utf-8");

        Task task = new Task("Task", "Description", Status.NEW, 0, Duration.ofMinutes(30),
                LocalDateTime.now());
        String jsonInputString = gson.toJson(task);

        try (var os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        assertEquals(201, responseCode);

        Scanner scanner = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8);
        String responseBody = scanner.useDelimiter("\\A").next();
        scanner.close();
        Task createdTask = gson.fromJson(responseBody, Task.class);
        int taskId = createdTask.getId();

        url = new URL("http://localhost:8080/tasks/" + taskId);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        responseCode = connection.getResponseCode();
        assertEquals(200, responseCode);

        url = new URL("http://localhost:8080/history/");
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        responseCode = connection.getResponseCode();
        assertEquals(200, responseCode);

        scanner = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8);
        responseBody = scanner.useDelimiter("\\A").next();
        scanner.close();

        Task[] history = gson.fromJson(responseBody, Task[].class);
        assertEquals(1, history.length);
        assertEquals("Task", history[0].getTaskName());
    }
}
*/
