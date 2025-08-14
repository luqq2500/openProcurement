package company.event;

import ddd.DomainEvent;

import java.time.Instant;

public class CompanyRegistrationRequestVerificationVerified implements DomainEvent {
    private final String verifiedBy;
    private final Instant verifiedOn;

    public CompanyRegistrationRequestVerificationVerified(String verifiedBy) {
        this.verifiedBy = verifiedBy;
        this.verifiedOn = Instant.now();
    }

    @Override
    public Instant getTimestamp() {
        return this.verifiedOn;
    }
}
