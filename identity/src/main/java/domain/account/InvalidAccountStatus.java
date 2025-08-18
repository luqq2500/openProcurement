package domain.account;

public class InvalidAccountStatus extends RuntimeException {
    public InvalidAccountStatus(String message) {
        super(message);
    }
}
