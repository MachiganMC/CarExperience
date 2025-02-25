package be.machigan.carexperience.exception;

public class NameAlreadyInUseException extends RuntimeException {
    public NameAlreadyInUseException(String message) {
        super(message);
    }
}
