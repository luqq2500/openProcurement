package usecase.registration.events;

import event.DomainEvent;
import applications.registration.RegistrationRequest;

import java.time.Instant;

public class RegistrationRequested implements DomainEvent {
    private final RegistrationRequest request;
    private final Instant requestedOn;
    public RegistrationRequested(RegistrationRequest request) {
        this.request = request;
        this.requestedOn = Instant.now();
    }
    @Override
    public Instant getTimestamp() {
        return requestedOn;
    }
}
