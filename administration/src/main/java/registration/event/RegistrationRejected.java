package registration.event;

import event.IntegrationEvent;

import java.time.Instant;
import java.util.UUID;

public class RegistrationRejected implements IntegrationEvent {
    private final UUID eventId;
    private final UUID registrationId;
    private final UUID rejectedByAdministratorId;
    private final Instant rejectedOn;

    public RegistrationRejected(UUID registrationId, UUID rejectedByAdministratorId) {
        this.eventId = UUID.randomUUID();
        this.registrationId = registrationId;
        this.rejectedByAdministratorId = rejectedByAdministratorId;
        this.rejectedOn = Instant.now();
    }

    @Override
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public String getEventName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public Instant getEventTime() {
        return rejectedOn;
    }

    public UUID getRegistrationId() {
        return registrationId;
    }

    public UUID getRejectedByAdministratorId() {
        return rejectedByAdministratorId;
    }
}
