package domain.account.exception;

public class InvalidAccountStatus extends RuntimeException {
    public InvalidAccountStatus(String message) {
        super(message);
    }
}
