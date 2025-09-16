package usecase.invitation.exception;

public class InvalidUserInvitation extends RuntimeException {
    public InvalidUserInvitation(String message) {
        super(message);
    }
}
