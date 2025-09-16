package usecase.registration.events.integration;

import domain.registration.CompanyDetails;
import event.IntegrationEvent;

import java.time.Instant;
import java.util.UUID;

public class RegistrationSubmitted_IE implements IntegrationEvent {
    private final UUID eventId;
    private final UUID registrationId;
    private final CompanyDetails companyDetails;
    private final Instant submittedOn;
    public RegistrationSubmitted_IE(UUID applicationId, CompanyDetails companyDetails) {
        this.eventId = UUID.randomUUID();
        this.registrationId = applicationId;
        this.companyDetails = companyDetails;
        this.submittedOn = Instant.now();
    }

    @Override
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public String getEventType() {
        return this.getClass().getSimpleName();
    }
    @Override
    public Instant getTimestamp() {
        return submittedOn;
    }
    public UUID getRegistrationId() {
        return registrationId;
    }
    public CompanyDetails getCompanyDetails() {return companyDetails;}
}
