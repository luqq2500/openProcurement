package domain.registration.events.domain;

import domain.company.CompanyDetails;
import domain.registration.AccountAdministratorDetails;
import domain.registration.RegistrationApplication;
import event.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public class RegistrationApplicationSubmitted implements DomainEvent {
    private final UUID registrationId;
    private final String companyName;
    private final String brn;
    private final String username;
    private final Instant submittedOn;
    public RegistrationApplicationSubmitted(RegistrationApplication registration) {
        this.registrationId = registration.getId();
        this.companyName = registration.getCompanyDetails().companyName();
        this.brn = registration.getCompanyDetails().brn();
        this.username = registration.getAccountAdministratorDetails().username();
        this.submittedOn = Instant.now();
    }
    @Override
    public Instant getTimestamp() {
        return submittedOn;
    }

    public UUID getRegistrationId() {
        return registrationId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getBrn() {
        return brn;
    }

    public String getUsername() {
        return username;
    }
}
