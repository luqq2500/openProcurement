package company.exception;

public class CompanyRegistrationApplicationAlreadyExist extends RuntimeException {
    public CompanyRegistrationApplicationAlreadyExist(String message) {
        super(message);
    }
}
