package modelTest;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TaskTest {

    // проверка, что экземпляры Task равны друг другу, если равен их id
/*    @Test
    void taskEqualsById() {
        Task task1 = new Task("Task 1", "Description 1", Status.NEW, 1);
        Task task2 = new Task("Task 2", "Description 2", Status.IN_PROGRESS, 1);

        Assertions.assertEquals(task1,task2);

    }
    //проверка, что наследники класса Task равны друг другу, если равен их id;
    @Test
    void taskInheritanceEqualsById() {
        Epic epic1 = new Epic("Epic 1", "Description 1", Status.NEW, 1);
        Epic epic2 = new Epic("Epic 2", "Description 2", Status.IN_PROGRESS, 2);
        Epic epic3 = new Epic("Epic 3", "Description 3", Status.DONE, 1);

        Subtask subtask1 = new Subtask("Subtask 1", "Description 1", Status.NEW, 1, epic1);
        Subtask subtask2 = new Subtask("Subtask 2", "Description 2", Status.IN_PROGRESS, 2, epic2);
        Subtask subtask3 = new Subtask("Subtask 3", "Description 3", Status.DONE, 1, epic3);

        Assertions.assertEquals(epic1, epic3);
        Assertions.assertEquals(subtask1, subtask3);

        Assertions.assertNotEquals(epic1, epic2);
        Assertions.assertNotEquals(subtask1, subtask2);
    }*/
}