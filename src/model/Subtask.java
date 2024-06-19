package model;

import java.util.Objects;

public class Subtask extends Task {
    private Epic epic;

    public Subtask(String taskName, String taskDescription, Status status, int id, Epic epic) {
        super(taskName, taskDescription, status, id);
        this.epic = epic;
    }

    public Epic getEpic() {
        return epic;
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }



/*
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Subtask)) return false;
        if (!super.equals(object)) return false;
        Subtask subtask = (Subtask) object;
        return Objects.equals(getTaskName(), subtask.getTaskName()) &&
                Objects.equals(getTaskDescription(), subtask.getTaskDescription()) &&
                getStatus() == subtask.getStatus() &&
                Objects.equals(epic, subtask.epic);
    }
*/
@Override
public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    if (!super.equals(object)) return false;
    Subtask subtask = (Subtask) object;
    return getId() == subtask.getId();
}

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId());
    }
}
