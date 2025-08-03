package company.exception;

public class CompanyRegistrationRequestExpired extends RuntimeException {
    public CompanyRegistrationRequestExpired(String s) {
        super(s);
    }
}
