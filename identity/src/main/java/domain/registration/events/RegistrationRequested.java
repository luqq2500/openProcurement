package domain.registration.events;

import event.IntegrationEvent;

import java.time.Instant;
import java.util.UUID;

public class RegistrationRequested implements IntegrationEvent {
    private final UUID eventId;
    private final String email;
    private final Instant requestedOn;

    public RegistrationRequested(String email) {
        this.eventId = UUID.randomUUID();
        this.email = email;
        this.requestedOn = Instant.now();
    }

    @Override
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public String getEventName() {
        return RegistrationRequested.class.getSimpleName();
    }

    @Override
    public Instant getTimestamp() {
        return requestedOn;
    }

    public String getEmail() {
        return email;
    }
}
