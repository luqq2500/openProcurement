package administrator.exception;

public class NotAuthorizedAdministratorRoleResponsibility extends RuntimeException {
    public NotAuthorizedAdministratorRoleResponsibility(String message) {
        super(message);
    }
}
