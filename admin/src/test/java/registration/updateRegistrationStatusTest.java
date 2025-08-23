package registration;

import administrator.spi.AdministratorRepository;
import identity.registration.RegistrationStatus;
import org.junit.Test;
import port.IntegrationEventPublisher;
import identity.registration.api.RegistrationStatusUpdater;
import identity.registration.event.MockRegistrationAdministeredPublisher;
import identity.registration.spi.RegistrationRepository;
import usecase.UpdateRegistrationStatus;

import java.util.UUID;

public class updateRegistrationStatusTest {
    @Test
    public void test() {
        RegistrationRepository registrationRepository = new InMemoryRegistrationRepository();
        AdministratorRepository administratorRepository = new InMemoryAdministratorRepository();
        IntegrationEventPublisher approvedEventPublisher = new MockRegistrationAdministeredPublisher();
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
