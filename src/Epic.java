import java.util.ArrayList;
public class Epic extends Task {
    ArrayList<Subtask> subtasks;
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

}
