package be.machigan.carexperience.exception;

public class TooManyAttemptsException extends RuntimeException {
    public TooManyAttemptsException(String message) {
        super(message);
    }

    public TooManyAttemptsException() {
        this("You've made too many attempts. Please try later");
    }
}
