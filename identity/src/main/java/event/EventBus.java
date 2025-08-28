package event;

public interface EventBus {
    <T extends DomainEvent> void publish(T event);
    void subscribe(DomainEventHandler<? extends DomainEvent> eventHandler);
}
