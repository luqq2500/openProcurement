package company.exception;

public class InvalidCompanyRegistrationNumber extends RuntimeException {
    public InvalidCompanyRegistrationNumber(String s) {
        super(s);
    }
}
