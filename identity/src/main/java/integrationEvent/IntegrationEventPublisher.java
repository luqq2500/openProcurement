package integrationEvent;

public interface IntegrationEventPublisher <T extends IntegrationEvent> {
    public void publish(T event);
}
