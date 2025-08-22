package registration;

import administrator.spi.AdministratorRepository;
import org.junit.Test;
import port.IntegrationEventPublisher;
import registration.api.RegistrationStatusUpdater;
import registration.event.MockRegistrationApprovedPublisher;
import registration.event.MockRegistrationRejectedPublisher;
import registration.spi.RegistrationRepository;
import usecase.UpdateRegistrationStatus;

import java.util.UUID;

public class updateRegistrationStatusTest {
    @Test
    public void test() {
        RegistrationRepository registrationRepository = new InMemoryRegistrationRepository();
        AdministratorRepository administratorRepository = new InMemoryAdministratorRepository();
        IntegrationEventPublisher approvedEventPublisher = new MockRegistrationApprovedPublisher();
        IntegrationEventPublisher rejectedEventPublisher = new MockRegistrationRejectedPublisher();

        RegistrationStatusUpdater registrationStatusUpdater = new UpdateRegistrationStatus(
                registrationRepository,
                administratorRepository,
                approvedEventPublisher,
                rejectedEventPublisher
        );
        registrationStatusUpdater.update(UUID.randomUUID(), UUID.randomUUID(), RegistrationStatus.APPROVE);
    }
}
