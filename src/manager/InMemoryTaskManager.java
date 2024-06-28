package manager;

import model.Epic;
import model.Subtask;
import model.Task;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryTaskManager implements TaskManager {
    private Map<Integer, Task> tasks;
    private HistoryManager historyManager;
    private Set<Task> prioritizedTasks;

    public InMemoryTaskManager() {
        this.tasks = new HashMap<>();
        this.historyManager = Managers.getDefaultHistory();
        this.prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime, Comparator.nullsLast(LocalDateTime::compareTo)));
    }

    private boolean isSubtaskOfEpic(Subtask subtask, Epic epic) {
        return subtask.getEpic().equals(epic);
    }

    @Override
    public void addTask(Task task) {
        if (!isOverlapping(task)) {
            tasks.put(task.getId(), task);
            if (task.getStartTime() != null) {
                prioritizedTasks.add(task);
            }
        } else {
            throw new IllegalArgumentException("Время выполнения задачи пересекается с существующими задачами");
        }
    }

    @Override
    public void updateTask(Task updateTask) {
        if (!isOverlapping(updateTask)) {
            tasks.put(updateTask.getId(), updateTask);
            prioritizedTasks.removeIf(task -> task.getId() == updateTask.getId());
            if (updateTask.getStartTime() != null) {
                prioritizedTasks.add(updateTask);
            }
        } else {
            throw new IllegalArgumentException("Время обновленной задачи пересекается с существующими задачами");
        }
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
        Task removedTask = tasks.remove(id);
        if (removedTask != null && removedTask.getStartTime() != null) {
            prioritizedTasks.remove(removedTask);
        }
        historyManager.remove(id);
    }

    @Override
    public Collection<Task> getAllTasks() {
        return tasks.values().stream().collect(Collectors.toList());
    }

    @Override
    public void clearAllTasks() {
        tasks.clear();
        prioritizedTasks.clear();
        historyManager.getHistory().forEach(task -> historyManager.remove(task.getId()));
    }

    @Override
    public Collection<Subtask> getAllSubtasksOfEpic(Epic epic) {
        return tasks.values().stream()
                .filter(task -> task instanceof Subtask && isSubtaskOfEpic((Subtask) task, epic))
                .map(task -> (Subtask) task)
                .collect(Collectors.toList());
    }

    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTasks);
    }

    public boolean isOverlapping(Task newTask) {
        return prioritizedTasks.stream()
                .anyMatch(task -> task.getStartTime() != null && task.getEndTime() != null
                && newTask.getStartTime() != null && newTask.getEndTime() != null
                && newTask.getStartTime().isBefore(task.getEndTime())
                && newTask.getEndTime().isAfter(task.getStartTime()));
    }

    @Override
    public Collection<Task> getHistory() {
        return historyManager.getHistory();
    }
}
