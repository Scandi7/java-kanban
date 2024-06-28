package modelTest;

import model.Epic;
import model.Status;
import model.Subtask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class EpicTest {
    private Epic epic;
    private Subtask subtask1;
    private Subtask subtask2;

    @BeforeEach
    void setUp() {
        epic = new Epic("Epic 1", "Description Epic", Status.NEW, 1, Duration.ZERO, null);
        subtask1 = new Subtask("Subtask 1", "Description Subtask", Status.NEW, 2, epic,
                Duration.ofMinutes(20), LocalDateTime.now());
        subtask2 = new Subtask("Subtask 2", "Description Subtask 2", Status.NEW, 3, epic,
                Duration.ofMinutes(30), LocalDateTime.now().plusMinutes(30));
    }

    @Test
    void calculateStatusAllNew() {
        epic.getSubtasks().add(subtask1);
        epic.getSubtasks().add(subtask2);
        assertEquals(Status.NEW, epic.calculateStatus());
    }

    @Test
    void calculateStatusAllDone() {
        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.DONE);
        epic.getSubtasks().add(subtask1);
        epic.getSubtasks().add(subtask2);
        assertEquals(Status.DONE, epic.calculateStatus());
    }

    @Test
    void calculateStatusNewAndDone() {
        subtask1.setStatus(Status.NEW);
        subtask2.setStatus(Status.DONE);
        epic.getSubtasks().add(subtask1);
        epic.getSubtasks().add(subtask2);
        assertEquals(Status.IN_PROGRESS, epic.calculateStatus());
    }

    @Test
    void calculateStatusInProgress() {
        subtask1.setStatus(Status.IN_PROGRESS);
        subtask2.setStatus(Status.IN_PROGRESS);
        epic.getSubtasks().add(subtask1);
        epic.getSubtasks().add(subtask2);
        assertEquals(Status.IN_PROGRESS, epic.calculateStatus());
    }

   /*@Test
    void cantBeAddedToItselfAsSubtask() {//объект Epic нельзя добавить в самого себя в виде подзадачи;

       Epic epic = new Epic("Epic 1", "Epic Description 1", Status.NEW, 1);
        Subtask subtask = new Subtask("Subtask 1", "Subtask Description 1", Status.NEW, 2,epic);

        Assertions.assertFalse(epic.getSubtasks().contains(epic));
    }*/
}