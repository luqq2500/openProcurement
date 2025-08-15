package user.event;

import event.DomainEvent;

import java.time.Instant;

public class UserAuthenticationFailed implements DomainEvent {
    private final Instant failedOn;

    public UserAuthenticationFailed() {
        this.failedOn = Instant.now();
    }

    @Override
    public Instant getTimestamp() {
        return failedOn;
    }
}
