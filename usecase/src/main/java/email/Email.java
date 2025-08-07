package email;

import java.util.UUID;

public class Email {
    private final UUID id;
    private final String to;
    private final String subject;
    private final String body;
    public Email(String to, String subject, String body) {
        this.id = UUID.randomUUID();
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    public UUID getId() {
        return id;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }
}
