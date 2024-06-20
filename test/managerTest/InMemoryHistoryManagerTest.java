package managerTest;

import manager.HistoryManager;
import manager.InMemoryHistoryManager;
import manager.Managers;
import model.Status;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InMemoryHistoryManagerTest {
    @Test
    void addTaskToHistory() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        Task task = new Task("Task", "Description", Status.NEW, 1);

        historyManager.add(task);

        Assertions.assertEquals(1, historyManager.getHistory().size());
        Assertions.assertTrue(historyManager.getHistory().contains(task));
    }

    @Test
    void addTaskAndCheckOrder() {
        HistoryManager historyManager = Managers.getDefaultHistory();

        Task task1 = new Task("Task1", "Description 1", Status.NEW, 1);
        Task task2 = new Task("Task2", "Description 2", Status.DONE, 2);
        Task task3 = new Task("Task3", "Description 3", Status.IN_PROGRESS, 3);

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);

        Collection<Task> historyCollection = historyManager.getHistory();
        List<Task> history = new ArrayList<>(historyCollection);

        Assertions.assertEquals(task1, history.get(0));
        Assertions.assertEquals(task2, history.get(1));
        Assertions.assertEquals(task3, history.get(2));
    }
}
