package integrationEvent;

public interface IntegrationEventHandler <T extends IntegrationEvent> {
    public void handle(T event);
}
