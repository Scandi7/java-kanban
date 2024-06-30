package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import manager.TaskManager;
import model.Epic;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class EpicsHandler extends BaseHttpHandler {
    private final TaskManager taskManager;
    private final Gson gson;

    public EpicsHandler(TaskManager taskManager, Gson gson) {
        this.taskManager = taskManager;
        this.gson = gson;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();
            String[] pathParts = path.split("/");

            if (method.equals("GET")) {
                if (pathParts.length == 2) {
                    List<Epic> epics = taskManager.getAllTasks().stream()
                            .filter(task -> task instanceof Epic)
                            .map(task -> (Epic) task)
                            .collect(Collectors.toList());
                    sendText(exchange, gson.toJson(epics), 200);
                } else if (pathParts.length == 3) {
                    int id = Integer.parseInt(pathParts[2]);
                    Epic epic = (Epic) taskManager.getTaskById(id);
                    if (epic != null) {
                        sendText(exchange, gson.toJson(epic), 200);
                    } else {
                        sendNotFound(exchange);
                    }
                }
            } else if (method.equals("POST")) {
                Epic epic = readRequestBody(exchange, Epic.class);
                if (epic.getId() == 0) {
                    taskManager.addTask(epic);
                    sendText(exchange, gson.toJson(epic), 201);
                } else {
                    taskManager.updateTask(epic);
                    sendText(exchange, gson.toJson(epic), 201);
                }
            } else if (method.equals("DELETE")) {
                if (pathParts.length == 3) {
                    int id = Integer.parseInt(pathParts[2]);
                    taskManager.removeTaskById(id);
                    sendText(exchange, "", 200);
                } else {
                    sendNotFound(exchange);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendText(exchange, "Внутренняя ошибка сервера", 500);
        } finally {
            exchange.close();
        }
    }
}