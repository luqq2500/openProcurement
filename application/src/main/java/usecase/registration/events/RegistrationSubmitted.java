package usecase.registration.events;

import event.DomainEvent;
import domain.registration.CompanyDetails;
import domain.registration.Registration;

import java.time.Instant;
import java.util.UUID;

public class RegistrationSubmitted implements DomainEvent {
    private final Registration registration;
    private final Instant submittedOn;

    public RegistrationSubmitted(Registration registration) {
        this.registration = registration;
        this.submittedOn = Instant.now();
    }
    @Override
    public Instant getTimestamp() {
        return submittedOn;
    }
    public UUID getRegistrationId() {
        return registration.registrationId();
    }
    public CompanyDetails getCompanyDetails(){
        return registration.companyDetails();
    }
}
