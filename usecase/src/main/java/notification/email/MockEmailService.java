package notification.email;

import ddd.DomainService;
import notification.NotificationMessage;
import notification.NotificationService;

@DomainService
public class MockEmailService implements NotificationService {
    @Override
    public void send(NotificationMessage message) {
        System.out.println("Sending email to: " + message.destination() + "\nSubject: " + message.subject() + "\nBody: " + message.message());
    }
}
