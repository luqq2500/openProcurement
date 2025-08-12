package company.event;

import company.CompanyRegistrationApplication;

import java.time.Instant;

public class CompanyRegistrationApplied implements DomainEvent {
    private final String appliedBy;
    private final CompanyRegistrationApplication registrationApplication;
    private final Instant appliedOn;
    public CompanyRegistrationApplied(String appliedBy, CompanyRegistrationApplication registrationApplication) {
        this.appliedBy = appliedBy;
        this.registrationApplication = registrationApplication;
        this.appliedOn = Instant.now();
    }
    @Override
    public Instant getTimestamp() {
        return appliedOn;
    }
}
