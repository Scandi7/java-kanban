package manager;

import model.Task;

public class Node {
    Task task;
    Node prev;
    Node next;

    Node(Task task, Node prev, Node next) {
        this.task = task;
        this.prev = prev;
        this.next = next;
    }
}
