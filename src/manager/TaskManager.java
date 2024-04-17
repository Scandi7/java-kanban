package manager;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private HashMap<Integer, Task> tasks;
    public TaskManager() {
        this.tasks = new HashMap<>();
    }
    public void addTask(Task task) {
        tasks.put(task.getId(), task);
    }
    public void updateTask(Task updateTask) {
        tasks.put(updateTask.getId(), updateTask);
    }
    public Task getTaskById(int id) {
        return tasks.get(id);
    }
    public void removeTaskById(int id) {
        tasks.remove(id);
    }
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }
    public void clearAllTasks() {
        tasks.clear();
    }
    public ArrayList<Subtask> getAllSubtasksOfEpic(Epic epic) {
        ArrayList<Subtask> subtasksOfEpic = new ArrayList<>();
        for(Task task : tasks.values()){
            if(task instanceof Subtask && isSubtaskOfEpic((Subtask) task, epic)) {
                subtasksOfEpic.add((Subtask) task);
            }
        }
        return subtasksOfEpic;
    }
    private boolean isSubtaskOfEpic(Subtask subtask, Epic epic) {
        return subtask.getEpic().equals(epic);
    }
}
