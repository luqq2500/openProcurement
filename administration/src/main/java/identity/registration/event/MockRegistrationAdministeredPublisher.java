package identity.registration.event;

import port.IntegrationEventPublisher;

public class MockRegistrationAdministeredPublisher implements IntegrationEventPublisher<RegistrationAdministered> {
    @Override
    public void publish(RegistrationAdministered event) {
        System.out.println("Publishing event: " + event.getEventType() + " " + event.getEventId() + " " + event.getEventTime());
        System.out.println(event.getRegistrationId() + " " + event.getAdministeredBy());
    }
}
