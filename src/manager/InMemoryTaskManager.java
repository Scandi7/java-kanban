package manager;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private Map<Integer, Task> tasks;
    private HistoryManager historyManager;
    public InMemoryTaskManager() {
        this.tasks = new HashMap<>();
        this.historyManager = Managers.getDefaultHistory();
    }

    private boolean isSubtaskOfEpic(Subtask subtask, Epic epic) {
        return subtask.getEpic().equals(epic);
    }

    @Override
    public void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateTask(Task updateTask) {
        tasks.put(updateTask.getId(), updateTask);
    }

    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public void removeTaskById(int id) {
        tasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public Collection<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void clearAllTasks() {
        tasks.clear();
        for (Task task : historyManager.getHistory()) {
            historyManager.remove(task.getId());
        }
    }

    @Override
    public Collection<Subtask> getAllSubtasksOfEpic(Epic epic) {
        Collection<Subtask> subtasksOfEpic = new ArrayList<>();
        for(Task task : tasks.values()) {
            if(task instanceof Subtask && ((Subtask) task).getEpic().equals(epic)) {
                subtasksOfEpic.add((Subtask) task);
            }
        }
        return subtasksOfEpic;
    }

    @Override
    public Collection<Task> getHistory() {
        return historyManager.getHistory();
    }
}
