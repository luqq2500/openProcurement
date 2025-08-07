package email;

public interface EmailService {
    void send(String destination, String subject, String body);
}

