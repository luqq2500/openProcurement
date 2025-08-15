package event;

public interface DomainEventHandler <T extends DomainEvent> {
    public void handle(T event);
}
