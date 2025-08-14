package company.exception;

public class InvalidRegistrationApplication extends RuntimeException {
    public InvalidRegistrationApplication(String message) {
        super(message);
    }
}
