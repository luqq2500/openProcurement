package event;

import java.time.Instant;
import java.util.UUID;

public interface IntegrationEvent {
    UUID getEventId();
    String getEventName();
    Instant getEventTime();
}
