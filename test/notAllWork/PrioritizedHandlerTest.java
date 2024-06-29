/*
package handlerTest;

import com.google.gson.Gson;
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

import static org.junit.jupiter.api.Assertions.*;

public class PrioritizedHandlerTest extends BaseHttpHandlerTest{
    private final Gson gson = new Gson();

    @Test
    public void testGetPrioritizedTasks() throws IOException {
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

        url = new URL("http://localhost:8080/prioritized");
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        responseCode = connection.getResponseCode();
        assertEquals(200, responseCode);

        Scanner scanner = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8);
        String responseBody = scanner.useDelimiter("\\A").next();
        scanner.close();

        Task[] prioritizedTasks = gson.fromJson(responseBody, Task[].class);
        assertEquals(1, prioritizedTasks.length);
        assertEquals("Task", prioritizedTasks[0].getTaskName());
    }
}
*/
