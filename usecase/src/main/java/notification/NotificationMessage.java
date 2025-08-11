package notification;

public record NotificationMessage(
        String destination,
        String subject,
        String message
) {
}
