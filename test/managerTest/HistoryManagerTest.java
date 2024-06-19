package managerTest;

import manager.*;
import model.Status;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

public class HistoryManagerTest {

    @Test
    void tasksAddedToHistoryManagerSavingPreviousVersionAndData() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        TaskManager taskManager = new InMemoryTaskManager();
        Task task = new Task("Task 1", "Description 1", Status.NEW, 1);

        taskManager.addTask(task);
        historyManager.add(task);
        List<Task> history = (List<Task>) historyManager.getHistory();
        Task previousVersion = history.get(0);

        Assertions.assertEquals(Status.NEW, previousVersion.getStatus());
    }

    @Test
    void addAndRemove() {
        HistoryManager historyManager = new InMemoryHistoryManager();

        Task task1 = new Task("Task 1", "Description 1", Status.NEW, 1);
        Task task2 = new Task("Task 2", "Description 2", Status.NEW, 2);

        historyManager.add(task1);
        historyManager.add(task2);
        Collection<Task> history = historyManager.getHistory();

        Assertions.assertEquals(2, history.size());
        Assertions.assertTrue(history.contains(task1));
        Assertions.assertTrue(history.contains(task2));

        historyManager.remove(1);
        history = historyManager.getHistory();

        Assertions.assertEquals(1, history.size());
        Assertions.assertTrue(history.contains(task2));
        Assertions.assertFalse(history.contains(task1));
    }
}