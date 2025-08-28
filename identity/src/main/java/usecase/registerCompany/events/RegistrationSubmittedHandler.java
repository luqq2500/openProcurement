package usecase.registerCompany.events;

import event.DomainEventHandler;
import port.IntegrationEventPublisher;
import usecase.registerCompany.events.integration.RegistrationSubmittedIntegration;

public class RegistrationSubmittedHandler implements DomainEventHandler<RegistrationSubmitted> {
    private final IntegrationEventPublisher<RegistrationSubmittedIntegration> integrationEventPublisher;

    public RegistrationSubmittedHandler(IntegrationEventPublisher<RegistrationSubmittedIntegration> integrationEventPublisher) {
        this.integrationEventPublisher = integrationEventPublisher;
    }

    @Override
    public void handle(RegistrationSubmitted event) {
        integrationEventPublisher.publish(new RegistrationSubmittedIntegration(event.getApplicationId(), event.getCompanyDetails()));
    }

    @Override
    public Class<RegistrationSubmitted> getEvent() {
        return RegistrationSubmitted.class;
    }
}
