package company.exception;

public class CompanyRegistrationNotFound extends RuntimeException {
    public CompanyRegistrationNotFound(String message) {
        super(message);
    }
}
