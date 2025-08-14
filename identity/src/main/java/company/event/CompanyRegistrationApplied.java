package company.event;

import ddd.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public class CompanyRegistrationApplied implements DomainEvent {
    private final String appliedBy;
    private final UUID registrationApplicationId;
    private final Instant appliedOn;
    public CompanyRegistrationApplied(String appliedBy, UUID registrationApplicationId) {
        this.appliedBy = appliedBy;
        this.registrationApplicationId = registrationApplicationId;
        this.appliedOn = Instant.now();
    }
    @Override
    public Instant getTimestamp() {
        return appliedOn;
    }
    public String getAppliedBy() {
        return appliedBy;
    }
    public UUID getRegistrationApplicationId() {
        return registrationApplicationId;
    }
}
