package identity.registration.event;

import event.IntegrationEvent;
import identity.registration.SubmittedRegistration;

import java.time.Instant;
import java.util.UUID;

public class RegistrationAdministered implements IntegrationEvent {
    private static final String EVENT_TYPE = "registration-administered";
    private final UUID eventId;
    private final UUID registrationId;
    private final boolean isApproved;
    private final UUID administeredBy;
    private final Instant administeredOn;
    public RegistrationAdministered(SubmittedRegistration submittedRegistration) {
        this.eventId = UUID.randomUUID();
        this.registrationId = submittedRegistration.requestId();
        this.isApproved = submittedRegistration.isApproved();
        this.administeredBy = submittedRegistration.administratorId();
        this.administeredOn = Instant.now();
    }
    @Override
    public UUID getEventId() {
        return eventId;
    }
    @Override
    public String getEventType() {
        return EVENT_TYPE;
    }
    @Override
    public Instant getEventTime() {
        return administeredOn;
    }
    public UUID getRegistrationId() {
        return registrationId;
    }
    public UUID getAdministeredBy() {
        return administeredBy;
    }
}
