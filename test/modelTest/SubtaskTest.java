package modelTest;

import model.Epic;
import model.Status;
import model.Subtask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SubtaskTest {
  /*  @Test
    void subTaskCantBeMadeOwnEpic() {//объект Subtask нельзя сделать своим же эпиком;
        Epic epic = new Epic("Epic 1", "Description 1", Status.NEW, 1);
        Subtask subtask = new Subtask("Subtask 1", "Description 1", Status.NEW, 2, epic);

        subtask.setEpic(subtask.getEpic());

        Assertions.assertNotEquals(subtask, subtask.getEpic());
    }*/
}
