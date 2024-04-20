package manager;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;

public interface TaskManager {
     void addTask(Task task);

    void updateTask(Task updateTask);

    Task getTaskById(int id);

    void removeTaskById(int id);

    ArrayList<Task> getAllTasks();

    void clearAllTasks();

    ArrayList<Subtask> getAllSubtasksOfEpic(Epic epic);
    ArrayList<Task> getHistory();
}
