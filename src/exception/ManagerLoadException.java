package exception;

public class ManagerLoadException extends RuntimeException {
    public ManagerLoadException(String message, Throwable reason) {
        super(message, reason);
    }
}
