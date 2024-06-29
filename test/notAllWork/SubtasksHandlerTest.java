/*
package handlerTest;

import adapter.DurationAdapter;
import adapter.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Epic;
import model.Status;
import model.Subtask;
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

public class SubtasksHandlerTest extends BaseHttpHandlerTest{
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Duration.class, new DurationAdapter())
            .registerTypeAdapter(LocalDateTimeAdapter.class, new LocalDateTimeAdapter())
            .create();

    @Test
    public void testAddSubtask() throws IOException {
        URL url = new URL("http://localhost:8080/epics");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json; utf-8");

        Epic epic = new Epic("Epic", "Description", Status.NEW, 0, Duration.ZERO, null);
        String jsonInputString = gson.toJson(epic);

        try (var os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        assertEquals(201, responseCode);

        Scanner scanner = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8);
        String responseBody = scanner.useDelimiter("\\A").next();
        scanner.close();
        Epic createdEpic = gson.fromJson(responseBody, Epic.class);
        int epicId = createdEpic.getId();

        url = new URL("http://localhost:8080/subtasks");
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json; utf-8");

        Subtask subtask = new Subtask("Subtask", "Description", Status.NEW, 0, createdEpic,
                Duration.ofMinutes(30), LocalDateTime.now());
        jsonInputString = gson.toJson(subtask);

        try (var os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);

            responseCode = connection.getResponseCode();
            assertEquals(201, responseCode);

            scanner = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8);
            responseBody = scanner.useDelimiter("\\A").next();
            scanner.close();

            Subtask createdSubtask = gson.fromJson(responseBody, Subtask.class);
            assertEquals("Subtask", createdSubtask.getTaskName());
            assertEquals(epicId, createdSubtask.getEpic().getId());
        }
    }

}

*/