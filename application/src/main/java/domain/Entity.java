package domain;

import event.DomainEvent;
import usecase.event.InMemoryEventBus;

public abstract class Entity {
    protected void publishEvent(DomainEvent domainEvent){
        InMemoryEventBus.INSTANCE.publish(domainEvent);
    }
}
