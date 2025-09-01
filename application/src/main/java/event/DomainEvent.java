package event;

import java.time.Instant;

public interface DomainEvent {
    Instant getTimestamp();
}
