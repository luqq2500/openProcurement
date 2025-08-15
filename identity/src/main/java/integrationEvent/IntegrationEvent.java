package integrationEvent;

import java.time.Instant;

public interface IntegrationEvent {
    Instant getTimestamp();
}
