package identities.registration;

import java.time.LocalDateTime;
import java.util.UUID;

public class RegistrationRequest {
    private final UUID id;
    private final UUID guessAccountId;
    private final LocalDateTime requestDate;
    public RegistrationRequest(UUID guessAccountId) {
        this.guessAccountId = guessAccountId;
        this.id = UUID.randomUUID();
        this.requestDate = LocalDateTime.now();
    }
    public UUID getId() {return id;}
    public LocalDateTime getRequestDate() {
        return requestDate;
    }
    public UUID getGuessAccountId() {return guessAccountId;}
}
