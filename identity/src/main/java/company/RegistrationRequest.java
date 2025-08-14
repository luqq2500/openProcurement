package company;

import java.time.LocalDateTime;
import java.util.UUID;

public class RegistrationRequest {
    private final UUID id;
    private final String email;
    private final LocalDateTime requestDate;
    private final LocalDateTime expiryDate;

    public RegistrationRequest(String email, LocalDateTime expiryDate) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.requestDate = LocalDateTime.now();
        this.expiryDate = expiryDate;
    }
    public boolean isValid(){
        return LocalDateTime.now().isBefore(expiryDate);
    }
    public UUID getId() {return id;}
    public LocalDateTime getRequestDate() {
        return requestDate;
    }
    public LocalDateTime getExpiryTime() {return expiryDate;}
    public String getEmail() {return email;}
}
