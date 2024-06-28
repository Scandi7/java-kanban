package ExceptionTest;

import exception.ManagerLoadException;
import exception.ManagerSaveException;
import manager.FileBackedTaskManager;
import model.Status;
import model.Task;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTaskManagerExceptionTest {

    @Test
    void saveException() {
        File file = new File("invalid_path/tasks.csv");
        FileBackedTaskManager manager = new FileBackedTaskManager(file);
        Task task = new Task("Task", "Description", Status.NEW, 1, Duration.ofMinutes(30),
                LocalDateTime.now());
        assertThrows(ManagerSaveException.class, () -> manager.addTask(task), "Должно выбрасываться ManagerSaveException");
    }

    @Test
    void loadException() {
        File file = new File("invalid_path/tasks.csv");
        assertThrows(ManagerLoadException.class, () -> FileBackedTaskManager.loadFromFile(file), "Должно выбрасываться" +
                " исключение ManagerLoadException");
    }

    @Test
    void loadValidFile() throws IOException {
        File file = new File("valid_tasks.csv");
        FileBackedTaskManager manager = new FileBackedTaskManager(file);
        Task task = new Task("Task", "Description", Status.NEW, 1, Duration.ofMinutes(30),
                LocalDateTime.now());
        manager.addTask(task);

        assertDoesNotThrow(() -> FileBackedTaskManager.loadFromFile(file), "При загрузке валидного файла не должно" +
                " быть исключения");
    }
}
