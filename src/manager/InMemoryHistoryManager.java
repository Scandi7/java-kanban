package manager;
import model.Task;
import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    private final ArrayList<Task> history;
    private static final int MAX_HISTORY_SIZE = 10;

    public InMemoryHistoryManager() {
        this.history = new ArrayList<>();
    }
    /*@Override
    public void add(Task task) {
        if (!history.contains(task)) {
            if (history.size() >= MAX_HISTORY_SIZE) {
                history.remove(0);
            }
            history.add(task);
        } else {
            history.remove(task);
            history.add(task);
        }
    }*/
    @Override
    public void add(Task task) {
        history.add(task);
        if (history.size() > MAX_HISTORY_SIZE) {
            history.remove(0);
        }
    }
    @Override
    public ArrayList<Task> getHistory() {
        return new ArrayList<>(history);
    }
}
