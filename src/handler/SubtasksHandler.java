package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import manager.TaskManager;
import model.Subtask;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class SubtasksHandler extends BaseHttpHandler {
    private final TaskManager taskManager;
    private final Gson gson;

    public SubtasksHandler(TaskManager taskManager, Gson gson) {
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
                    List<Subtask> subtasks = taskManager.getAllTasks().stream()
                            .filter(task -> task instanceof Subtask)
                            .map(task -> (Subtask) task)
                            .collect(Collectors.toList());
                    sendText(exchange, gson.toJson(subtasks), 200);
                } else if (pathParts.length == 3) {
                    int id = Integer.parseInt(pathParts[2]);
                    Subtask subtask = (Subtask) taskManager.getTaskById(id);
                    if (subtask != null) {
                        sendText(exchange, gson.toJson(subtask), 200);
                    } else {
                        sendNotFound(exchange);
                    }
                }
            } else if (method.equals("POST")) {
                Subtask subtask = readRequestBody(exchange, Subtask.class);
                if (subtask.getId() == 0) {
                    taskManager.addTask(subtask);
                    sendText(exchange, gson.toJson(subtask), 201);
                } else {
                    taskManager.updateTask(subtask);
                    sendText(exchange, gson.toJson(subtask), 201);
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