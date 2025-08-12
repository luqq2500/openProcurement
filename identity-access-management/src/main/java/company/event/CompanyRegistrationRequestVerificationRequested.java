package company.event;

import java.time.Instant;

public class CompanyRegistrationRequestVerificationRequested implements DomainEvent {
    private final String requestedBy;
    private final Instant requestedOn;
    public CompanyRegistrationRequestVerificationRequested(String requestedBy) {
        this.requestedBy = requestedBy;
        this.requestedOn = Instant.now();
    }
    @Override
    public Instant getTimestamp() {
        return requestedOn;
    }
    public String getRequestedBy() {
        return requestedBy;
    }
}
