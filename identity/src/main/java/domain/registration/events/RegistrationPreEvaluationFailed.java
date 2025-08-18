package domain.registration.events;

import event.IntegrationEvent;

import java.time.Instant;
import java.util.UUID;

public class RegistrationPreEvaluationFailed implements IntegrationEvent {
    private final UUID eventId;
    private final UUID registrationId;
    private final Instant failedOn;

    public RegistrationPreEvaluationFailed(UUID registrationId) {
        this.eventId = UUID.randomUUID();
        this.registrationId = registrationId;
        this.failedOn = Instant.now();
    }

    @Override
    public UUID getEventId() {
        return null;
    }

    @Override
    public String getEventName() {
        return RegistrationPreEvaluationFailed.class.getSimpleName();
    }

    @Override
    public Instant getTimestamp() {
        return failedOn;
    }
}
