package usecase.registration.events.integration;

import event.IntegrationEvent;

import java.time.Instant;
import java.util.UUID;

public class RegistrationRequested_IE implements IntegrationEvent {
    private final static String EVENT_TYPE = "registration_requested";
    private final UUID eventId;
    private final String email;
    private final Instant requestedOn;

    public RegistrationRequested_IE(String email) {
        this.eventId = UUID.randomUUID();
        this.email = email;
        this.requestedOn = Instant.now();
    }

    @Override
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public String getEventType() {return EVENT_TYPE;}

    @Override
    public Instant getTimestamp() {
        return requestedOn;
    }

    public String getEmail() {
        return email;
    }
}
