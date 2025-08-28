package usecase.registerCompany.events;

import usecase.registerCompany.events.integration.RegistrationSubmittedIntegration;
import port.IntegrationEventPublisher;

public class MockRegistrationSubmittedPublisher implements IntegrationEventPublisher<RegistrationSubmittedIntegration> {
    @Override
    public void publish(RegistrationSubmittedIntegration event) {
        System.out.println("Publishing integration event...");
    }
}
