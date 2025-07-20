package administrator.exception;

public class AdministratorAlreadyExistException extends RuntimeException {
    public AdministratorAlreadyExistException(String message) {
        super(message);
    }
}
