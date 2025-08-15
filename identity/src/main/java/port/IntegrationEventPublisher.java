package port;

import event.IntegrationEvent;

public interface IntegrationEventPublisher <T extends IntegrationEvent> {
    public void publish(T event);
}
