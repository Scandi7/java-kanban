package model;

import java.util.ArrayList;
import java.util.Objects;

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
/*    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Epic)) return false;
        if (!super.equals(object)) return false;
        Epic epic = (Epic) object;
        return Objects.equals(getTaskName(), epic.getTaskName()) &&
                Objects.equals(getTaskDescription(), epic.getTaskDescription()) &&
                getStatus() == epic.getStatus();
    }
*/
@Override
public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    if (!super.equals(object)) return false;
    Epic epic = (Epic) object;
    return getId() == epic.getId();
}

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId());
    }
}