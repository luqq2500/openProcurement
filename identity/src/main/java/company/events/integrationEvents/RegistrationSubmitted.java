package company.events.integrationEvents;

import company.RegistrationApplication;
import event.IntegrationEvent;

import java.time.Instant;
import java.util.UUID;

public class RegistrationSubmitted implements IntegrationEvent {
    private final UUID eventId;
    private final UUID submittedRegistrationId;
    private final String submittedBy;
    private final Instant submittedOn;
    public RegistrationSubmitted(RegistrationApplication application) {
        this.eventId = UUID.randomUUID();
        this.submittedRegistrationId = application.getId();
        this.submittedBy = application.getAppliedByEmail();
        this.submittedOn = Instant.now();
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
    public Instant getTimestamp() {
        return submittedOn;
    }
    public String getSubmittedBy() {
        return submittedBy;
    }
    public UUID getSubmittedRegistrationId() {
        return submittedRegistrationId;
    }
}
