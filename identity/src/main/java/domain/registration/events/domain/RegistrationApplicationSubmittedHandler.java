package domain.registration.events.domain;

import domain.registration.api.RegistrationPreEvaluator;
import event.DomainEvent;
import event.DomainEventHandler;

public class RegistrationApplicationSubmittedHandler implements DomainEventHandler <RegistrationApplicationSubmitted> {
    private final RegistrationPreEvaluator preEvaluator;
    public RegistrationApplicationSubmittedHandler(RegistrationPreEvaluator preEvaluator) {
        this.preEvaluator = preEvaluator;
    }
    @Override
    public void handle(RegistrationApplicationSubmitted event) {
        preEvaluator.evaluate(event);
    }
    @Override
    public Class<RegistrationApplicationSubmitted> getEvent() {
        return RegistrationApplicationSubmitted.class;
    }
}
