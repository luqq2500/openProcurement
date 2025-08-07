package company.exception;

public class InvalidCompanyRegistrationApplication extends RuntimeException {
    public InvalidCompanyRegistrationApplication(String s) {
        super(s);
    }
}
