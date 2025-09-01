package usecase.registration.exception;

public class InvalidCompanyRegistration extends RuntimeException {
    public InvalidCompanyRegistration(String message) {
        super(message);
    }
}
