package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import manager.TaskManager;
import model.Task;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TasksHandler extends BaseHttpHandler {
    private final TaskManager taskManager;
    private final Gson gson;

    public TasksHandler(TaskManager taskManager, Gson gson) {
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
                    List<Task> tasks = taskManager.getAllTasks().stream()
                            .filter(task -> !(task instanceof model.Epic) && !(task instanceof model.Subtask))
                            .collect(Collectors.toList());
                    sendText(exchange, gson.toJson(tasks), 200);
                } else if (pathParts.length == 3) {
                    int id = Integer.parseInt(pathParts[2]);
                    Task task = taskManager.getTaskById(id);
                    if (task != null) {
                        sendText(exchange, gson.toJson(task), 200);
                    } else {
                        sendNotFound(exchange);
                    }
                }
            } else if (method.equals("POST")) {
                Task task = readRequestBody(exchange, Task.class);
                if (task.getId() == 0) {
                    taskManager.addTask(task);
                    sendText(exchange, gson.toJson(task), 201);
                } else {
                    taskManager.updateTask(task);
                    sendText(exchange, gson.toJson(task), 201);
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