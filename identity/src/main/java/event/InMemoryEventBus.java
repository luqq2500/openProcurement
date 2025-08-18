package event;

import annotation.DomainService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DomainService
public class InMemoryEventBus implements EventBus{
    private final Map<Class<? extends DomainEvent>, List<DomainEventHandler<? extends DomainEvent>>> handlers = new HashMap<>();
    private static final EventBus INSTANCE = new InMemoryEventBus();

    @Override
    public <T extends DomainEvent> void publish(T event) {
        Class<? extends DomainEvent> domainEvent = event.getClass();
        List<DomainEventHandler<? extends DomainEvent>> handlers = this.handlers.get(domainEvent);
        if (handlers != null) {
            for (DomainEventHandler<? extends DomainEvent> handler : handlers) {
                @SuppressWarnings("unchecked")
                DomainEventHandler<T> eventHandler = (DomainEventHandler<T>) handler;
                eventHandler.handle(event);
            }
        }
    }
    @Override
    public void subscribe(DomainEventHandler<? extends DomainEvent> eventHandler) {
        Class<? extends DomainEvent> domainEvent = eventHandler.getEvent();
        handlers.computeIfAbsent(domainEvent, k -> new ArrayList<>()).add(eventHandler);
    }
    @Override
    public EventBus getInstance() {
        return INSTANCE;
    }
}
