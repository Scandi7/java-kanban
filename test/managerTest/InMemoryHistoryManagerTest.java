package managerTest;

import manager.HistoryManager;
import manager.InMemoryHistoryManager;
import manager.Managers;
import model.Status;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryHistoryManagerTest {
    private InMemoryHistoryManager historyManager;
    private Task task;

    @BeforeEach
    void setUp() {
        historyManager = new InMemoryHistoryManager();
        task = new Task("Task 1", "Description 1", Status.NEW, 1, Duration.ofMinutes(30),
                LocalDateTime.now());
    }

    @Test
    void addAndRetrieveHistory() {
        historyManager.add(task);
        assertTrue(historyManager.getHistory().contains(task));
    }

    @Test
    void removeFromHistory() {
        historyManager.add(task);
        historyManager.remove(task.getId());
        assertFalse(historyManager.getHistory().contains(task));
    }

    @Test
    void emptyHistory() {
        assertTrue(historyManager.getHistory().isEmpty());
    }

    @Test
    void duplicateTaskHistory() {
        historyManager.add(task);
        historyManager.add(task);
        assertEquals(1, historyManager.getHistory().size());
    }

    @Test
    void removeFromBeginningOfHistory() {
        Task task2 = new Task("Task 2", "Description 2", Status.NEW, 2, Duration.ofMinutes(45),
                LocalDateTime.now().plusMinutes(30));
        historyManager.add(task);
        historyManager.add(task2);
        historyManager.remove(task.getId());
        assertEquals(1, historyManager.getHistory().size());
        assertFalse(historyManager.getHistory().contains(task));
    }

    @Test
    void removeFromEndOfHistory() {
        Task task2 = new Task("Task 2", "Description 2", Status.NEW, 2, Duration.ofMinutes(45),
                LocalDateTime.now().plusMinutes(30));
        historyManager.add(task);
        historyManager.add(task2);
        historyManager.remove(task2.getId());
        assertEquals(1, historyManager.getHistory().size());
        assertFalse(historyManager.getHistory().contains(task2));
    }

    @Test
    void removeFromMiddleOfHistory() {
        Task task2 = new Task("Task 2", "Description 2", Status.NEW, 2, Duration.ofMinutes(45),
                LocalDateTime.now().plusMinutes(30));
        Task task3 = new Task("Task 3", "Description 3", Status.NEW, 3, Duration.ofMinutes(60),
                LocalDateTime.now().plusHours(1));
        historyManager.add(task);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.remove(task2.getId());
        assertEquals(2, historyManager.getHistory().size());
        assertFalse(historyManager.getHistory().contains(task2));
    }

/*    @Test
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
    }*/
}
