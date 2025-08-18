package domain.registration.events;

import domain.registration.CompanyDetails;
import event.IntegrationEvent;

import java.time.Instant;
import java.util.UUID;

public class RegistrationPreEvaluationPassed implements IntegrationEvent {
    private final UUID eventId;
    private final UUID registrationId;
    private final CompanyDetails companyDetails;
    private final Instant passedOn;

    public RegistrationPreEvaluationPassed(UUID registrationId, CompanyDetails companyDetails) {
        this.eventId = UUID.randomUUID();
        this.registrationId = registrationId;
        this.companyDetails = companyDetails;
        this.passedOn = Instant.now();
    }

    @Override
    public UUID getEventId() {return eventId;}

    @Override
    public String getEventName() {return RegistrationPreEvaluationPassed.class.getSimpleName();}

    @Override
    public Instant getTimestamp() {
        return passedOn;
    }

    public UUID getRegistrationId() {
        return registrationId;
    }

    public CompanyDetails getCompanyDetails() {
        return companyDetails;
    }
}
