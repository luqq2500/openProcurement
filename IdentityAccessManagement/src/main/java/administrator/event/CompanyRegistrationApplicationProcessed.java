package administrator.event;

import company.event.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public class CompanyRegistrationApplicationProcessed implements DomainEvent {
    private final UUID administratorId;
    private final UUID companyRegistrationApplicationId;
    private final Instant processedOn;

    public CompanyRegistrationApplicationProcessed(UUID administratorId, UUID companyRegistrationApplicationId) {
        this.administratorId = administratorId;
        this.companyRegistrationApplicationId = companyRegistrationApplicationId;
        this.processedOn = Instant.now();
    }
    @Override
    public Instant getTimestamp() {
        return processedOn;
    }
}
