package company.exception;

public class InvalidRegisterCompanyCommand extends RuntimeException {
    public InvalidRegisterCompanyCommand(String message) {
        super(message);
    }
}
