package managerTest;

import manager.InMemoryTaskManager;
import manager.TaskManager;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest<T extends TaskManager> {
    protected T taskManager;
    protected Task task;
    protected Epic epic;
    protected Subtask subtask;

    @BeforeEach
    void setUp() {
        task = new Task("Task 1", "Description 1", Status.NEW, 1, Duration.ofMinutes(30),
                LocalDateTime.now());
        epic = new Epic("Epic 1", "Description Epic", Status.NEW, 2, Duration.ZERO,
                null);
        subtask = new Subtask("Subtask 1", "Description Subtask", Status.NEW, 3, epic,
                Duration.ofMinutes(20), LocalDateTime.now().plusMinutes(30));
    }

    @Test
    void addTask() {
        taskManager.addTask(task);
        assertEquals(task, taskManager.getTaskById(task.getId()));
    }

    @Test
    void updateTask() {
        taskManager.addTask(task);
        Task updateTask = new Task("Updated task 1", "Updated Description 1", Status.IN_PROGRESS,
                1, Duration.ofMinutes(45), LocalDateTime.now().plusHours(1));
        taskManager.updateTask(updateTask);
        assertEquals(updateTask, taskManager.getTaskById(task.getId()));
    }

    @Test
    void getTaskById() {
        taskManager.addTask(task);
        assertEquals(task, taskManager.getTaskById(task.getId()));
    }

    @Test
    void removeTaskById() {
        taskManager.addTask(task);
        taskManager.removeTaskById(task.getId());
        assertNull(taskManager.getTaskById(task.getId()));
    }

    @Test
    void clearAllTasks() {
        taskManager.addTask(task);
        taskManager.clearAllTasks();
        assertTrue(taskManager.getAllTasks().isEmpty());
    }

    @Test
    void getAllSubtasksOffEpic() {
        taskManager.addTask(epic);
        taskManager.addTask(subtask);
        assertTrue(taskManager.getAllSubtasksOfEpic(epic).contains(subtask));
    }

    @Test
    void testOverlappingTasks() {
        taskManager.addTask(task);
        Task overlappingTask = new Task("Overlapping task", "Overlapping description", Status.NEW,
                4, Duration.ofMinutes(30), task.getStartTime().plusMinutes(15));
        assertThrows(IllegalArgumentException.class, () -> taskManager.addTask(overlappingTask));
    }
}
