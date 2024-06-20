package managerTest;

import manager.FileBackedTaskManager;
import manager.TaskManager;

import model.Task;
import model.Status;
import model.Subtask;
import model.Epic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class FileBackedManagerTest {

    @Test
    void saveAndLoadEmptyFile() throws IOException {
        File tempFile = File.createTempFile("taskManager", ".csv");
        FileBackedTaskManager manager = new FileBackedTaskManager(tempFile);

        manager.clearAllTasks();

        TaskManager loadedManager = FileBackedTaskManager.loadFromFile(tempFile);

        Assertions.assertTrue(loadedManager.getAllTasks().isEmpty());
    }

    @Test
    public void saveAndLoadMultipleTasks() throws IOException {

        File tempFile = File.createTempFile("taskManager", ".csv");
        FileBackedTaskManager manager = new FileBackedTaskManager(tempFile);

        Task task1 = new Task("Task 1", "Description 1", Status.NEW, 1);
        Epic epic1 = new Epic("Epic 1", "Description 2", Status.NEW, 2);
        Subtask subtask1 = new Subtask("Subtask 1", "Description 3", Status.NEW, 3, epic1);

        manager.addTask(task1);
        manager.addTask(epic1);
        manager.addTask(subtask1);

        FileBackedTaskManager loadManager = FileBackedTaskManager.loadFromFile(tempFile);

        Collection<Task> tasks = loadManager.getAllTasks();

        Assertions.assertTrue(tasks.contains(task1));
        Assertions.assertTrue(tasks.contains(epic1));
        Assertions.assertTrue(tasks.contains(subtask1));
    }
}
