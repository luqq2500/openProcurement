package usecase.registerCompany.events;

import event.DomainEvent;
import identities.registration.CompanyDetails;
import identities.registration.RegistrationApplication;

import java.time.Instant;
import java.util.UUID;

public class RegistrationSubmitted implements DomainEvent {
    private final RegistrationApplication application;
    private final Instant submittedOn;

    public RegistrationSubmitted(RegistrationApplication application) {
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
