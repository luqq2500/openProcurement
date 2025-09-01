package port;

import event.IntegrationEvent;

public interface IntegrationEventPublisher<T extends IntegrationEvent> {
    void publish(T event);
}
