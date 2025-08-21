package registration.event;

import event.IntegrationEvent;

import java.time.Instant;
import java.util.UUID;

public class RegistrationApproved implements IntegrationEvent {
    private final UUID eventId;
    private final UUID registrationId;
    private final UUID approvedByAdministratorId;
    private final Instant approvedOn;
    public RegistrationApproved(UUID registrationId, UUID approvedByAdministratorId) {
        this.eventId = UUID.randomUUID();
        this.registrationId = registrationId;
        this.approvedByAdministratorId = approvedByAdministratorId;
        this.approvedOn = Instant.now();
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
        return approvedOn;
    }
    public UUID getRegistrationId() {
        return registrationId;
    }
    public UUID getApprovedByAdministratorId() {
        return approvedByAdministratorId;
    }
}
