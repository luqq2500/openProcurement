package port;

import event.IntegrationEvent;

public interface IntegrationEventHandler <T extends IntegrationEvent> {
    void handle(T event);
}
