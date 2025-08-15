package company.event;

import integrationEvent.IntegrationEvent;

import java.time.Instant;
import java.util.UUID;

public class CompanyRegistrationApplied implements IntegrationEvent {
    private final String appliedByEmail;
    private final UUID registrationApplicationId;
    private final Instant appliedOn;
    public CompanyRegistrationApplied(String appliedByEmail, UUID registrationApplicationId) {
        this.appliedByEmail = appliedByEmail;
        this.registrationApplicationId = registrationApplicationId;
        this.appliedOn = Instant.now();
    }
    @Override
    public Instant getTimestamp() {
        return appliedOn;
    }
    public String getAppliedByEmail() {
        return appliedByEmail;
    }
    public UUID getRegistrationApplicationId() {
        return registrationApplicationId;
    }
}
