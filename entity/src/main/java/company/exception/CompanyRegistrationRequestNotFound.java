package company.exception;

public class CompanyRegistrationRequestNotFound extends RuntimeException {
    public CompanyRegistrationRequestNotFound(String message) {
        super(message);
    }
}
