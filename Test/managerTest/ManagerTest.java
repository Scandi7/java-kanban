package managerTest;

import manager.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ManagerTest { //утилитарный класс всегда возвращает проинициализированные и готовые к работе экземпляры менеджеров;
    @Test
    void defaultHistoryManager() {
        HistoryManager historyManager = Managers.getDefaultHistory();

        Assertions.assertNotNull(historyManager);
        Assertions.assertTrue(historyManager instanceof InMemoryHistoryManager);
    }
    @Test
    void defaultTaskManager() {
        TaskManager taskManager = Managers.getDefaultTaskManager();
        Assertions.assertNotNull(taskManager);
        Assertions.assertTrue(taskManager instanceof InMemoryTaskManager);
    }
}
