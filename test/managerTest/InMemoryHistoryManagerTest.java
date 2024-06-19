package managerTest;

import manager.HistoryManager;
import manager.InMemoryHistoryManager;
import model.Status;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InMemoryHistoryManagerTest {
    @Test
    void addTaskToHistory() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        Task task = new Task("Task", "Description", Status.NEW, 1);

        historyManager.add(task);

        Assertions.assertEquals(1, historyManager.getHistory().size());
        Assertions.assertTrue(historyManager.getHistory().contains(task));
    }
}
