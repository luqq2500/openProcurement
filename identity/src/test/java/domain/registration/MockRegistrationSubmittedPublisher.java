package domain.registration;

import domain.registration.events.RegistrationSubmitted;
import port.IntegrationEventPublisher;

public class MockRegistrationSubmittedPublisher implements IntegrationEventPublisher<RegistrationSubmitted> {
    @Override
    public void publish(RegistrationSubmitted event) {
        System.out.println("MockRegistrationSubmittedPublisher.publish(RegistrationSubmitted) called");
    }
}
