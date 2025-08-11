package notification;

public class InvalidNotificationType extends RuntimeException {
    InvalidNotificationType(String message) {
        super(message);
    }
}
