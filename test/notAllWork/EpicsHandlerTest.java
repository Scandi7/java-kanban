/*
package handlerTest;

import adapter.DurationAdapter;
import adapter.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Epic;
import model.Status;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EpicsHandlerTest extends BaseHttpHandlerTest {
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Duration.class, new DurationAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    @Test
    public void testAddEpic() throws IOException {
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

        try (Scanner scanner = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8)) {
            String responseBody = scanner.useDelimiter("\\A").next();
            Epic createdEpic = gson.fromJson(responseBody, Epic.class);
            assertEquals("Epic", createdEpic.getTaskName());
        }
    }
}
*/
