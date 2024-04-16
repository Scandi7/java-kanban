public class Main {

    public static void main(String[] args) {
        Task task1 = new Task("Task 1", "Description 1", Status.NEW, 1);
        Task task2 = new Task("Task 2", "Description 2", Status.IN_PROGRESS, 2);

        Epic epic1 = new Epic("Epic 1", "Description 1", Status.NEW, 3);
        Epic epic2 = new Epic("Epic 2", "Description 2", Status.NEW, 4);

        Subtask subtask1 = new Subtask("Subtask 1", "Description 1", Status.DONE, 5, epic1);
        Subtask subtask2 = new Subtask("Subtask 2", "Description 2", Status.IN_PROGRESS, 6, epic1);
        Subtask subtask3 = new Subtask("Subtask 3", "Description 3", Status.NEW, 7, epic2);

        TaskManager taskManager = new TaskManager();
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(epic1);
        taskManager.addTask(epic2);
        taskManager.addTask(subtask1);
        taskManager.addTask(subtask2);
        taskManager.addTask(subtask3);

        System.out.println("Список эпиков:");

        for(Task task : taskManager.getAllTasks()) {
            if(task instanceof Epic) {
                System.out.println(task);
            }
        }
        System.out.println("Список задач:");

        for(Task task : taskManager.getAllTasks()) {
            System.out.println(task);
        }
        System.out.println("Список всех подзадач определенного эпика:");

        for(Task task : taskManager.getAllTasks()) {
            if(task instanceof Subtask) {
                Subtask subtask = (Subtask) task;
                if(subtask.getEpic().equals(epic1)) {
                    System.out.println(subtask);
                }
            }
        }
        task1.setStatus(Status.DONE);
        subtask2.setStatus(Status.DONE);

        System.out.println("Измененные статусы задач и подзадач:");

        for(Task task : taskManager.getAllTasks()) {
            System.out.println(task);
        }

        taskManager.removeTaskById(1);
        taskManager.removeTaskById(5);
        taskManager.removeTaskById(6);

        System.out.println("Список задач после удаления:");

        for (Task task : taskManager.getAllTasks()) {
            System.out.println(task);
        }
    }
}
