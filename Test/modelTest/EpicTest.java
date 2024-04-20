package modelTest;

import model.Epic;
import model.Status;
import model.Subtask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EpicTest {
   @Test
    void cantBeAddedToItselfAsSubtask() {//объект Epic нельзя добавить в самого себя в виде подзадачи;

       Epic epic = new Epic("Epic 1", "Epic Description 1", Status.NEW, 1);
        Subtask subtask = new Subtask("Subtask 1", "Subtask Description 1", Status.NEW, 2,epic);

        Assertions.assertFalse(epic.getSubtasks().contains(epic));
    }
}