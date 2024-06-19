package manager;

import model.Epic;
import model.Subtask;
import model.Task;
import java.util.Collection;

public interface TaskManager {
     void addTask(Task task);

    void updateTask(Task updateTask);

    Task getTaskById(int id);

    void removeTaskById(int id);

    Collection<Task> getAllTasks();

    void clearAllTasks();

    Collection<Subtask> getAllSubtasksOfEpic(Epic epic);

    Collection<Task> getHistory();
}