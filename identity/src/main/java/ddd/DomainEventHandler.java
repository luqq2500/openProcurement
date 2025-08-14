package ddd;

public interface DomainEventHandler <T extends DomainEvent> {
    public void handle(T event);
}
