package managerTest;

import manager.HistoryManager;
import manager.InMemoryHistoryManager;
import manager.InMemoryTaskManager;
import manager.TaskManager;
import model.Status;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;

public class HistoryManagerTest {
    //задачи, добавляемые в HistoryManager, сохраняют предыдущую версию задачи и её данных
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
}