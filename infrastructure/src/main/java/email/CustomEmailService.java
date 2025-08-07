package email;

import org.springframework.stereotype.Service;

@Service
public class CustomEmailService implements EmailService {
    private final EmailRepository emailRepository;
    public CustomEmailService(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }
    @Override
    public void send(String destination, String subject, String body) {
        Email email = new Email(destination, subject, body);
        emailRepository.add(email);
        System.out.println("Email sent: " + email.getTo() + email.getSubject() + email.getBody());
    }
}
