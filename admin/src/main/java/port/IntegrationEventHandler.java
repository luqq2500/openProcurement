package port;

import event.IntegrationEvent;

public interface IntegrationEventHandler {
    void handle(String message);
}
