package port;

import event.IntegrationEvent;

public interface IntegrationEventPublisher {
    void publish(IntegrationEvent event);
}
