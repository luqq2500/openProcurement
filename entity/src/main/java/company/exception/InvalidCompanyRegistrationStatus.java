package company.exception;

public class InvalidCompanyRegistrationStatus extends RuntimeException {
    public InvalidCompanyRegistrationStatus(String s) {
        super(s);
    }
}
