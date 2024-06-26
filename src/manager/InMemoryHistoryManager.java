package manager;

import model.Task;

import java.util.*;
import java.util.stream.Collectors;

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

        if (tail != null) {
            tail.setNext(newNode);
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
        if (node.getNext() != null) {
            node.getNext().setPrev(node.getPrev());
        } else {
            tail = node.getPrev();
        }
        if (node.getPrev() != null) {
            node.getPrev().setNext(node.getNext());
        } else {
            head = node.getNext();
        }
    }

    @Override
    public Collection<Task> getHistory() {
        return taskMap.values().stream()
                .sorted(Comparator.comparingInt(node -> {
                    Node current = head;
                    int index = 0;
                    while (current != null) {
                        if (current.equals(node)) {
                            break;
                        }
                        current = current.getNext();
                        index++;
                    }
                    return index;
                }))
                .map(Node::getTask)
                .collect(Collectors.toList());
    }
}
