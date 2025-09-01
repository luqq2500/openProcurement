package registration;

import administration.registration.event.RegistrationAdministered;
import port.IntegrationEventPublisher;

public class MockRegistrationAdministeredPublisher implements IntegrationEventPublisher<RegistrationAdministered> {
    @Override
    public void publish(RegistrationAdministered event) {
        System.out.println("Publishing " + event.getClass().getSimpleName());
    }
}
