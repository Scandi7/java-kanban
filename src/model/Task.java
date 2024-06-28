package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    private String taskName;
    private String taskDescription;
    private Status status;
    private int id;
    private Duration duration;
    private LocalDateTime startTime;

    public Task(String taskName, String taskDescription, Status status, int id, Duration duration, LocalDateTime startTime) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.status = status;
        this.id = id;
        this.duration = duration;
        this.startTime = startTime;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    Task task = (Task) object;
    return id == task.id;
}

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTaskDescription(),getTaskName(),getStatus(), getDuration(), getStartTime());
    }

    @Override
    public String toString() {
        return getTaskName() + ", " + getTaskDescription() + ", ID: " + getId() + ", Status: " + getStatus()+
                ", Duration: " + getDuration().toMinutes() + " mins, StartTime: " +getStartTime();
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        if (startTime != null && duration != null) {
            return startTime.plus(duration);
        }
        return null;
    }
}
