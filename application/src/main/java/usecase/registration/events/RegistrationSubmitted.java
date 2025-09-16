package usecase.registration.events;

import event.DomainEvent;
import domain.registration.CompanyDetails;
import domain.registration.Registration;

import java.time.Instant;
import java.util.UUID;

public class RegistrationSubmitted implements DomainEvent {
    private final Registration application;
    private final Instant submittedOn;

    public RegistrationSubmitted(Registration application) {
        this.application = application;
        this.submittedOn = Instant.now();
    }
    @Override
    public Instant getTimestamp() {
        return submittedOn;
    }
    public UUID getApplicationId() {
        return application.getApplicationId();
    }
    public CompanyDetails getCompanyDetails(){
        return application.companyDetails();
    }
}
