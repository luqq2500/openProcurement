package usecase.registration.events.integration;

import port.IntegrationEventPublisher;

public class MockRegistrationSubmittedIEPublisher implements IntegrationEventPublisher<RegistrationSubmitted_IE> {
    @Override
    public void publish(RegistrationSubmitted_IE event) {
        System.out.println("Publishing integration event...");
    }
}
