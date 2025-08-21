package registration;

import org.junit.Test;
import port.IntegrationEventPublisher;
import registration.api.RegistrationStatusUpdater;
import registration.event.MockRegistrationApprovedPublisher;
import registration.event.MockRegistrationRejectedPublisher;
import registration.event.RegistrationApproved;
import registration.event.RegistrationRejected;
import registration.usecase.UpdateRegistrationStatus;

import java.util.UUID;

public class updateRegistrationStatusTest {
    @Test
    public void test() {
        IntegrationEventPublisher<RegistrationApproved> approvedEventPublisher = new MockRegistrationApprovedPublisher();
        IntegrationEventPublisher<RegistrationRejected> rejectedEventPublisher = new MockRegistrationRejectedPublisher();
        RegistrationStatusUpdater registrationStatusUpdater = new UpdateRegistrationStatus(approvedEventPublisher, rejectedEventPublisher);
        registrationStatusUpdater.update(UUID.randomUUID(), UUID.randomUUID(), RegistrationStatus.APPROVE);
    }
}
