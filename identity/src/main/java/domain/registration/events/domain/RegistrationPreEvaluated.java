package domain.registration.events.domain;

import domain.registration.RegistrationApplicationStatus;
import event.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public class RegistrationPreEvaluated implements DomainEvent {
    private final UUID registrationId;
    private final RegistrationApplicationStatus status;
    private final Instant failedOn;
    public RegistrationPreEvaluated(UUID registrationId, RegistrationApplicationStatus status) {
        this.registrationId = registrationId;
        this.status = status;
        this.failedOn = Instant.now();
    }
    @Override
    public Instant getTimestamp() {
        return failedOn;
    }
}
