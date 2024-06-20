package managerTest;

import manager.TaskManager;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import manager.InMemoryTaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InMemoryTaskManagerTest {
    //InMemoryTaskManager действительно добавляет задачи разного типа и может найти их по id;
    @Test
    void addAndGetTaskById() {
        TaskManager taskManager = new InMemoryTaskManager();

        Task task = new Task("Task 1", "Task Description 1", Status.NEW, 1);
        Epic epic = new Epic("Epic 1", "Epic Description 1", Status.IN_PROGRESS, 2);
        Subtask subtask = new Subtask("Subtask 1", "Description 1", Status.DONE, 3, epic);

        taskManager.addTask(task);
        taskManager.addTask(epic);
        taskManager.addTask(subtask);

        Assertions.assertEquals(3, taskManager.getAllTasks().size());

        Assertions.assertEquals(task, taskManager.getTaskById(1));
        Assertions.assertEquals(epic, taskManager.getTaskById(2));
        Assertions.assertEquals(subtask, taskManager.getTaskById(3));
    }

    //задачи с заданным id и сгенерированным id не конфликтуют внутри менеджера;
    @Test
    void tasksWithGivenAndGeneratedIdDontConflictInsideManager() {
        TaskManager taskManager = new InMemoryTaskManager();

        Task taskWithId = new Task("Task with Id", "Description", Status.NEW, 1);
        Task taskWithGeneratedId = new Task("Task with Generated Id", "Description", Status.DONE, 2);

        taskManager.addTask(taskWithId);
        taskManager.addTask(taskWithGeneratedId);

        Assertions.assertEquals(2, taskManager.getAllTasks().size());

        Assertions.assertEquals(taskWithId, taskManager.getTaskById(1));
        Assertions.assertEquals(taskWithGeneratedId, taskManager.getTaskById(2));
    }

    //проверяется неизменность задачи (по всем полям) при добавлении задачи в менеджер
    @Test
    void immutabilityTaskWhenAddingTaskToManager() {
        TaskManager taskManager = new InMemoryTaskManager();
        Task task = new Task("Task", "Description", Status.NEW, 1);
        taskManager.addTask(task);
        Task addedTask = taskManager.getTaskById(task.getId());

        Assertions.assertEquals(task, addedTask);
    }
}
