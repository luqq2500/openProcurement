package company.events;

import event.IntegrationEvent;

import java.time.Instant;
import java.util.UUID;

public class RegistrationApplied implements IntegrationEvent {
    @Override
    public UUID getEventId() {
        return null;
    }

    @Override
    public String getEventName() {
        return "";
    }

    @Override
    public Instant getTimestamp() {
        return null;
    }
}
