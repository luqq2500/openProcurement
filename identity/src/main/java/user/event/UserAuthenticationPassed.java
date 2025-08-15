package user.event;

import domainEvent.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public class UserAuthenticationPassed implements DomainEvent {
    private final UUID userId;
    private final Instant authOn;
    public UserAuthenticationPassed(UUID userId) {
        this.userId = userId;
        this.authOn = Instant.now();
    }
    @Override
    public Instant getTimestamp() {
        return authOn;
    }
    public UUID getUserId() {
        return userId;
    }
}
