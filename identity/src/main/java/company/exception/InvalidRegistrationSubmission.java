package company.exception;

public class InvalidRegistrationSubmission extends RuntimeException {
    public InvalidRegistrationSubmission(String message) {
        super(message);
    }
}
