package notification;

public record NotificationServiceCommand(String destination, String subject, String message) {
}
