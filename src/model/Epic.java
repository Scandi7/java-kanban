package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class Epic extends Task {
    private Collection<Subtask> subtasks;

    public Epic(String taskName, String taskDescription, Status status, int id, Duration duration, LocalDateTime startTime) {
        super(taskName, taskDescription, status, id, duration, startTime);
        this.subtasks = new ArrayList<>();
    }

    public Collection<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(Collection<Subtask> subtasks) {
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

    @Override
    public Duration getDuration() {
        return subtasks.stream()
                .map(Subtask::getDuration)
                .reduce(Duration.ZERO, Duration::plus);
    }

    @Override
    public LocalDateTime getStartTime() {
        return subtasks.stream()
                .map(Subtask::getStartTime)
                .filter(Objects::nonNull)
                .min(LocalDateTime::compareTo)
                .orElse(null);
    }

    @Override
    public LocalDateTime getEndTime() {
        return subtasks.stream()
                .map(Subtask::getEndTime)
                .filter(Objects::nonNull)
                .max(LocalDateTime::compareTo)
                .orElse(null);
    }
}