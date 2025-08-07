package email;

public class MockEmailService implements EmailService {
    @Override
    public void send(String destination, String subject, String body) {
        System.out.println("Sending email to: " + destination + " with subject " + subject + " and body " + body);
    }
}
