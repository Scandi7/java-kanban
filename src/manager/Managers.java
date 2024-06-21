package manager;

import java.io.File;

public class Managers {
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static TaskManager getDefaultTaskManager() {
        return new InMemoryTaskManager();
    }

    public static FileBackedTaskManager getDefaultFileBackedTaskManager(File file) {
        return new FileBackedTaskManager(file);
    }
}
