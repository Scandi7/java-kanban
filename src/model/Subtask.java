package model;

public class Subtask extends Task {
    private Epic epic;
    public Subtask(String taskName, String taskDescription, Status status, int id, Epic epic){
        super(taskName, taskDescription, status, id);
        this.epic = epic;
    }

    public Epic getEpic() {
        return epic;
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }
}
//