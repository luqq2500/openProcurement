package registration.event;

import port.IntegrationEventPublisher;

public class MockRegistrationApprovedPublisher implements IntegrationEventPublisher<RegistrationApproved> {
    @Override
    public void publish(RegistrationApproved event) {
        System.out.println("Publishing event: " + event.getEventName() + " " + event.getEventId() + " " + event.getEventTime());
        System.out.println(event.getRegistrationId() + " " + event.getApprovedByAdministratorId());
    }
}
