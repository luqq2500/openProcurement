package ddd;

import java.time.Instant;

public interface DomainEvent {
    Instant getTimestamp();
}
