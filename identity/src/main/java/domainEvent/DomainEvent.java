package domainEvent;

import java.time.Instant;

public interface DomainEvent {
    Instant getTimestamp();
}
