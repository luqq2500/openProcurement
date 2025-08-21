package registration.event;

import port.IntegrationEventPublisher;

public class MockRegistrationRejectedPublisher implements IntegrationEventPublisher<RegistrationRejected> {
    @Override
    public void publish(RegistrationRejected event) {
        System.out.println("Publishing event: " + event.getEventName() + " " + event.getEventId() + " " + event.getEventTime());
        System.out.println(event.getRegistrationId() + " " + event.getRejectedByAdministratorId());
    }
}
