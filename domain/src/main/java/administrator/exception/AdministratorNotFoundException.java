package administrator.exception;

public class AdministratorNotFoundException extends RuntimeException {
    public AdministratorNotFoundException(String message) {
        super(message);
    }
}
