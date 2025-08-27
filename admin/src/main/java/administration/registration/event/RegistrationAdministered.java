package administration.registration.event;

import administration.registration.RegistrationAdministration;
import event.IntegrationEvent;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public class RegistrationAdministered implements IntegrationEvent {
    private final UUID eventId;
    private final UUID applicationId;
    private final String status;
    private final UUID administeredBy;
    private final LocalDateTime administeredOn;
    private final Instant publishedOn;
    public RegistrationAdministered(RegistrationAdministration administration) {
        this.eventId = UUID.randomUUID();
        this.applicationId = administration.applicationId();
        this.status = administration.status().name();
        this.administeredBy = administration.administratorId();
        this.administeredOn = administration.administeredOn();
        this.publishedOn = Instant.now();
    }
    @Override
    public UUID getEventId() {
        return eventId;
    }
    @Override
    public Instant getEventTime() {
        return publishedOn;
    }
    public UUID getApplicationId() {
        return applicationId;
    }
    public UUID getAdministeredBy() {
        return administeredBy;
    }
}
