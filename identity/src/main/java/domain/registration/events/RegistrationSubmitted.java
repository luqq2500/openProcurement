package domain.registration.events;

import domain.registration.CompanyDetails;
import domain.registration.RegistrationApplication;
import event.DomainEvent;
import event.IntegrationEvent;

import java.time.Instant;
import java.util.UUID;

public class RegistrationSubmitted implements IntegrationEvent {
    private final UUID eventId;
    private final UUID registrationId;
    private final CompanyDetails companyDetails;
    private final Instant submittedOn;
    public RegistrationSubmitted(RegistrationApplication registration) {
        this.eventId = UUID.randomUUID();
        this.registrationId = registration.getId();
        this.companyDetails = registration.getCompanyDetails();
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
    public UUID getRegistrationId() {
        return registrationId;
    }
    public CompanyDetails getCompanyDetails() {return companyDetails;}
}
