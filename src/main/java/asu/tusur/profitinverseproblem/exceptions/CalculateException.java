package asu.tusur.profitinverseproblem.exceptions;

public class CalculateException extends Throwable {
    public CalculateException(String message) {
        super(message);
    }

    public CalculateException(String message, Throwable cause) {
        super(message, cause);
    }
}
