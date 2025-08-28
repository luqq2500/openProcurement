package event;

import annotation.DomainService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

@DomainService
public enum InMemoryEventBus implements EventBus{
    INSTANCE;

    private final ConcurrentHashMap<Class<? extends DomainEvent>, ConcurrentLinkedQueue<DomainEventHandler<? extends DomainEvent>>> handlers = new ConcurrentHashMap<>();

    @Override
    public <T extends DomainEvent> void publish(T event) {
        Class<? extends DomainEvent> domainEvent = event.getClass();
        ConcurrentLinkedQueue<DomainEventHandler<? extends DomainEvent>> eventHandlers = handlers.get(domainEvent);
        if (eventHandlers != null) {
            for (DomainEventHandler<? extends DomainEvent> handler : eventHandlers) {
                @SuppressWarnings("unchecked")
                DomainEventHandler<T> eventHandler = (DomainEventHandler<T>) handler;
                eventHandler.handle(event);
            }
        }
    }

    @Override
    public void subscribe(DomainEventHandler<? extends DomainEvent> eventHandler) {
        Class<? extends DomainEvent> domainEvent = eventHandler.getEvent();
        handlers.computeIfAbsent(domainEvent, k -> new ConcurrentLinkedQueue<>()).add(eventHandler);
    }
}
