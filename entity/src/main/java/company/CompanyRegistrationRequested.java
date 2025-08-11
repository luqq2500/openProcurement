package company;

import ddd.DomainEvent;

import java.time.Instant;

public class CompanyRegistrationRequested implements DomainEvent {
    private final String requestedFrom;
    private final Instant occurredOn;
    public CompanyRegistrationRequested(String requestedFrom, Instant occurredOn) {
        this.requestedFrom = requestedFrom;
        this.occurredOn = Instant.now();
    }
    @Override
    public Instant getTimestamp() {
        return occurredOn;
    }
    public String getRequestedFrom() {
        return requestedFrom;
    }
}
