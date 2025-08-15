package administrator.event;


import event.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public class CompanyRegistrationApplicationApproved implements DomainEvent {
    private final UUID administratorId;
    private final UUID companyRegistrationApplicationId;
    private final Instant approvedOn;
    public CompanyRegistrationApplicationApproved(UUID administratorId, UUID companyRegistrationApplicationId) {
        this.administratorId = administratorId;
        this.companyRegistrationApplicationId = companyRegistrationApplicationId;
        this.approvedOn = Instant.now();
    }
    @Override
    public Instant getTimestamp() {
        return approvedOn;
    }
}
