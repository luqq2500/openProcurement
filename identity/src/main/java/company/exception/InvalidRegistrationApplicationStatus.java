package company.exception;

public class InvalidRegistrationApplicationStatus extends RuntimeException {
    public InvalidRegistrationApplicationStatus(String message) {
        super(message);
    }
}
