package company.exception;

public class InvalidCompanyRegistrationApplicationCommand extends RuntimeException {
    public InvalidCompanyRegistrationApplicationCommand(String message) {
        super(message);
    }
}
