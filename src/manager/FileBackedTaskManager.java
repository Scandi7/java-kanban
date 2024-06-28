package manager;

import exception.ManagerLoadException;
import exception.ManagerSaveException;
import model.Task;
import model.Status;
import model.Epic;
import model.Subtask;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void updateTask(Task updateTask) {
        super.updateTask(updateTask);
        save();
    }

    @Override
    public Task getTaskById(int id) {
        Task task = super.getTaskById(id);
        save();
        return task;
    }

    @Override
    public void removeTaskById(int id) {
        super.removeTaskById(id);
        save();
    }

    @Override
    public void clearAllTasks() {
        super.clearAllTasks();
        save();
    }

    @Override
    public Collection<Subtask> getAllSubtasksOfEpic(Epic epic) {
        Collection<Subtask> subtasks = super.getAllSubtasksOfEpic(epic);
        save();
        return subtasks;
    }

    private void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("id,type,name,status,description,duration,startTime,epic\n");
            for (Task task : getAllTasks()) {
                writer.write(taskToString(task) + "\n");
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при сохранении задач в файл", e);
        }
    }

    private String taskToString(Task task) {
        String type = "TASK";
        if (task instanceof Epic) {
            type = "EPIC";
        } else if (task instanceof Subtask) {
            type = "SUBTASK";
        }
        String epicId = "";
        if (task instanceof Subtask) {
            epicId = String.valueOf(((Subtask) task).getEpic().getId());
        }
        String startTime = task.getStartTime() != null ? task.getStartTime().format(formatter) : "";
        return String.format("%d,%s,%s,%s,%s,%d,%s,%s",
                task.getId(),
                type,
                task.getTaskName(),
                task.getStatus(),
                task.getTaskDescription(),
                task.getDuration().toMinutes(),
                startTime,
                epicId);
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager manager = new FileBackedTaskManager(file);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                Task task = fromString(line);
                if (task instanceof Epic) {
                    manager.addTask(task);
                } else if (task instanceof Subtask) {
                    manager.addTask(task);
                } else {
                    manager.addTask(task);
                }
            }
        } catch (IOException e) {
            throw new ManagerLoadException("Ошибка при загрузке задач из файла", e);
        }
        return manager;
    }

    private static Task fromString(String value) {
        String[] fields = value.split(",");
        int id = Integer.parseInt(fields[0]);
        String type = fields[1];
        String name = fields[2];
        Status status = Status.valueOf(fields[3]);
        String description = fields[4];
        Duration duration = Duration.ofMinutes(Long.parseLong(fields[5]));
        LocalDateTime startTime = LocalDateTime.parse(fields[6], formatter);

        switch (type) {
            case "TASK":
                return new Task(name, description, status, id, duration, startTime);
            case "EPIC":
                return new Epic(name, description, status, id, duration, startTime);
            case "SUBTASK":
                Epic epic = new Epic("", "", Status.NEW, Integer.parseInt(fields[7]), Duration.ZERO,null);
                return new Subtask(name, description, status, id, epic, duration, startTime);
            default:
                throw new IllegalArgumentException("Неизвестный тип" + type);
        }
    }
}
