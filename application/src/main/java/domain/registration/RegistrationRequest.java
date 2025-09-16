package domain.registration;

import java.time.LocalDateTime;
import java.util.UUID;

public class RegistrationRequest {
    private final UUID id;
    private final UUID accountId;
    private final LocalDateTime requestDate;
    public RegistrationRequest(UUID accountId) {
        this.accountId = accountId;
        this.id = UUID.randomUUID();
        this.requestDate = LocalDateTime.now();
    }
    public UUID getId() {return id;}
    public LocalDateTime getRequestDate() {
        return requestDate;
    }
    public UUID getAccountId() {return accountId;}
}
