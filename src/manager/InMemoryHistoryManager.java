package manager;
import model.Task;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node> taskMap = new HashMap<>();
    private Node head;
    private Node tail;

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }
        remove(task.getId());

        Node newNode = new Node(task, tail, null);

        if(tail != null) {
            tail.next = newNode;
        } else {
            head = newNode;
        }
        tail = newNode;
        taskMap.put(task.getId(), newNode);
    }

    @Override
    public void remove(int id) {
        Node node = taskMap.remove(id);

        if (node == null) {
            return;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
    }
    @Override
    public Collection<Task> getHistory() {
        Collection<Task> history = new ArrayList<>();
        Node current = head;
        while (current != null) {
            history.add(current.task);
            current = current.next;
        }
        return history;
    }
}
