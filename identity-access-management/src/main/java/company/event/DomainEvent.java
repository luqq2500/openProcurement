package company.event;

import java.time.Instant;

public interface DomainEvent {
    Instant getTimestamp();
}
