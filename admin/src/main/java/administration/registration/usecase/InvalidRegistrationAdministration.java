package administration.registration.usecase;

public class InvalidRegistrationAdministration extends RuntimeException {
    public InvalidRegistrationAdministration(String message) {
        super(message);
    }
}
