package domain.registration.exception;

public class InvalidRegistration extends RuntimeException {
    public InvalidRegistration(String message) {
        super(message);
    }
}
