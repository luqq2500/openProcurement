package usecase.registration.exception;

public class InvalidRegistrationRequest extends RuntimeException {
    public InvalidRegistrationRequest(String message) {
        super(message);
    }
}
