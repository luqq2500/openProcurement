package company;

public class InvalidCompanyRegistrationApplicationStatus extends RuntimeException {
    public InvalidCompanyRegistrationApplicationStatus(String message) {
        super(message);
    }
}
