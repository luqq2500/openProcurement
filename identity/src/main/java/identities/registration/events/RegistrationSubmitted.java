package identities.registration.events;

import identities.registration.CompanyDetails;
import identities.registration.RegistrationApplication;
import event.IntegrationEvent;

import java.time.Instant;
import java.util.UUID;

public class RegistrationSubmitted implements IntegrationEvent {
    private final static String EVENT_TYPE = "registration_submitted";
    private final UUID eventId;
    private final UUID registrationId;
    private final CompanyDetails companyDetails;
    private final Instant submittedOn;
    public RegistrationSubmitted(RegistrationApplication registration) {
        this.eventId = UUID.randomUUID();
        this.registrationId = registration.requestId();
        this.companyDetails = registration.companyDetails();
        this.submittedOn = Instant.now();
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
    public Instant getTimestamp() {
        return submittedOn;
    }
    public UUID getRegistrationId() {
        return registrationId;
    }
    public CompanyDetails getCompanyDetails() {return companyDetails;}
}
