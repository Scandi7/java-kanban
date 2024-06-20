package exception;

public class ManagerSaveException extends RuntimeException {
    public ManagerSaveException(String message, Throwable reason) {
        super(message, reason);
    }
}
