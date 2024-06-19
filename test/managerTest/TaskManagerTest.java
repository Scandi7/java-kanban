package managerTest;

import manager.InMemoryTaskManager;
import manager.TaskManager;
import model.Status;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TaskManagerTest {
    @Test
    public void addAndRemove() {
        TaskManager taskManager = new InMemoryTaskManager();

        Task task1 = new Task("Task 1", "Description 1", Status.NEW, 1);
        Task task2 = new Task("Task 2", "Description 2", Status.NEW, 2);

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        Task receivedTask1 = taskManager.getTaskById(1);
        Task receivedTask2 = taskManager.getTaskById(2);

        Assertions.assertEquals(task1, receivedTask1);
        Assertions.assertEquals(task2, receivedTask2);

        taskManager.removeTaskById(1);
        Task removedTask = taskManager.getTaskById(1);
        Assertions.assertNull(removedTask);
    }
}
