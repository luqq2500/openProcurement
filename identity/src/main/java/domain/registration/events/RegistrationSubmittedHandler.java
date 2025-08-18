package domain.registration.events;

import domain.registration.api.RegistrationPreEvaluator;
import event.DomainEventHandler;

public class RegistrationSubmittedHandler implements DomainEventHandler <RegistrationSubmitted> {
    private final RegistrationPreEvaluator preEvaluator;
    public RegistrationSubmittedHandler(RegistrationPreEvaluator preEvaluator) {
        this.preEvaluator = preEvaluator;
    }
    @Override
    public void handle(RegistrationSubmitted event) {
        preEvaluator.evaluate(event.getRegistrationId(), event.getCompanyName(), event.getBrn(), event.getUsername());
    }
    @Override
    public Class<RegistrationSubmitted> getEvent() {
        return RegistrationSubmitted.class;
    }
}
