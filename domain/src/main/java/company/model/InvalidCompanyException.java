package company.model;

public class InvalidCompanyException extends RuntimeException {
    public InvalidCompanyException(String message) {
        super(message);
    }
}
