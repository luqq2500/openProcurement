package administrator.event;


import domain.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public class CompanyRegistrationApplicationRejected implements DomainEvent {
    private final UUID administratorId;
    private final UUID companyRegistrationApplicationId;
    private final Instant rejectedOn;
    public CompanyRegistrationApplicationRejected(UUID administratorId, UUID companyRegistrationApplicationId) {
        this.administratorId = administratorId;
        this.companyRegistrationApplicationId = companyRegistrationApplicationId;
        this.rejectedOn = Instant.now();
    }
    @Override
    public Instant getTimestamp() {
        return rejectedOn;
    }
}
