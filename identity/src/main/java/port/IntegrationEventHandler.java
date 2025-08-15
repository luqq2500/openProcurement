package port;

import event.IntegrationEvent;

public interface IntegrationEventHandler <T extends IntegrationEvent> {
    public void handle(T event);
}
