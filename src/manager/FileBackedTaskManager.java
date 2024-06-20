package manager;

import exception.ManagerSaveException;
import model.Task;
import model.Status;
import model.Epic;
import model.Subtask;

import java.io.*;
import java.util.Collection;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file;

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
            writer.write("id,type,name,status,description,epic\n");
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
        return String.format("%d,%s,%s,%s,%s,%s",
                task.getId(),
                type,
                task.getTaskName(),
                task.getStatus(),
                task.getTaskDescription(),
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
            throw new ManagerSaveException("Ошибка при загрузке задач из файла", e);
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

        switch (type) {
            case "TASK":
                return new Task(name, description, status, id);
            case "EPIC":
                return new Epic(name, description, status, id);
            case "SUBTASK":
                Epic epic = new Epic("", "", Status.NEW, Integer.parseInt(fields[5]));
                return new Subtask(name, description, status, id, epic);
            default:
                throw new IllegalArgumentException("Неизвестный тип" + type);
        }
    }
}
