package administrator.exception;

public class InvalidRegisterAdministratorCommand extends RuntimeException {
    public InvalidRegisterAdministratorCommand(String message) {
        super(message);
    }
}
