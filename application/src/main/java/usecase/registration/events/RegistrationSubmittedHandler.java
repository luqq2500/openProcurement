package usecase.registration.events;

import event.DomainEventHandler;
import port.IntegrationEventPublisher;
import usecase.registration.events.integration.RegistrationSubmitted_IE;

public class RegistrationSubmittedHandler implements DomainEventHandler<RegistrationSubmitted> {
    private final IntegrationEventPublisher<RegistrationSubmitted_IE> integrationEventPublisher;

    public RegistrationSubmittedHandler(IntegrationEventPublisher<RegistrationSubmitted_IE> integrationEventPublisher) {
        this.integrationEventPublisher = integrationEventPublisher;
    }

    @Override
    public void handle(RegistrationSubmitted event) {
        integrationEventPublisher.publish(new RegistrationSubmitted_IE(event.getApplicationId(), event.getCompanyDetails()));
    }

    @Override
    public Class<RegistrationSubmitted> getEvent() {
        return RegistrationSubmitted.class;
    }
}
