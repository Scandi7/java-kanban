package main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpServer;
import handler.*;
import manager.TaskManager;
import util.DurationTypeAdapter;
import util.LocalDateTimeTypeAdapter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.LocalDateTime;

public class HttpTaskServer {
    private final HttpServer server;
    private final Gson gson;

    public HttpTaskServer(int port, TaskManager taskManager) throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(port), 0);
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Duration.class, new DurationTypeAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .create();
        server.createContext("/tasks", new TasksHandler(taskManager, gson));
        server.createContext("/subtasks", new SubtasksHandler(taskManager, gson));
        server.createContext("/epics", new EpicsHandler(taskManager, gson));
        server.createContext("/history", new HistoryHandler(taskManager, gson));
        server.createContext("/prioritized", new PrioritizedHandler(taskManager, gson));
    }

    public void start() {
        server.start();
        System.out.println("Сервер запущен на порту " + server.getAddress().getPort());
    }

    public void stop() {
        server.stop(0);
        System.out.println("Сервер остановлен");
    }
}
