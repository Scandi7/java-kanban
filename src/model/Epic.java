package model;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Subtask> subtasks;
    public Epic(String taskName, String taskDescription, Status status, int id) {
        super(taskName, taskDescription, status, id);
        this.subtasks = new ArrayList<>();
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(ArrayList<Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    public Status calculateStatus() {
        Status previousStatus = null;
        for (Subtask subtask : subtasks) {
            Status currentStatus = subtask.getStatus();
            if (previousStatus != null && currentStatus != previousStatus) {
                return Status.IN_PROGRESS;
            }
            previousStatus = currentStatus;
        }
        return (previousStatus != null) ? previousStatus : Status.NEW;
    }

}
