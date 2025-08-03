package notification;

public record NotificationCommand(String destination, String subject, String message) {
}
