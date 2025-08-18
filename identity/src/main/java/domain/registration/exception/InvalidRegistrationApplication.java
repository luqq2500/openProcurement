package domain.registration.exception;

public class InvalidRegistrationApplication extends RuntimeException {
    public InvalidRegistrationApplication(String message) {
        super(message);
    }
}
