package notification;

public class MockNotificationService implements NotificationService {
    @Override
    public void notify(NotificationServiceCommand command) {
        System.out.println(command.destination());
        System.out.println(command.subject());
        System.out.println(command.message());
    }
}
