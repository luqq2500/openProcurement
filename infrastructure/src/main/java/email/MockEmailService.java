package email;

import org.springframework.stereotype.Service;

@Service
public class MockEmailService implements EmailService {
    @Override
    public void send(String destination, String subject, String message) {
        System.out.println("Sending email to " + destination + " with subject " + subject + " and message " + message);
    }
}
