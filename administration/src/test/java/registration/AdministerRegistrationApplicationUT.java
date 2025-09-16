package registration;

import administration.address.Address;
import administration.AdministrationStatus;
import administrator.Administrator;
import administrator.AdministratorRole;
import administration.ApplicationAdministration;
import administrator.spi.AdministratorRepository;
import administration.registration.RegistrationAdministration;
import administration.registration.RegistrationApplication;
import administration.ApplicationAdministrator;
import administration.registration.event.RegistrationAdministered;
import administration.registration.usecase.InvalidRegistrationAdministration;
import administration.registration.spi.RegistrationRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import port.IntegrationEventPublisher;
import administration.registration.spi.RegistrationAdministrationRepository;
import administration.registration.usecase.AdministerRegistrationApplication;

import java.time.LocalDateTime;
import java.util.UUID;

public class AdministerRegistrationApplicationUT {
    private ApplicationAdministrator applicationAdministrator;
    private RegistrationAdministrationRepository administrationRepository;
    private AdministratorRepository administratorRepository;
    private RegistrationApplication appliedApplication;
    private Administrator identityAdministrator;

    @Before
    public void setUp() throws Exception {
        RegistrationRepository registrationRepository = new InMemoryRegistrationRepository();
        administrationRepository = new InMemoryRegistrationAdministrationRepository();
        administratorRepository = new InMemoryAdministratorRepository();
        IntegrationEventPublisher<RegistrationAdministered> eventPublisher = new MockRegistrationAdministeredPublisher();

        identityAdministrator = new Administrator(AdministratorRole.IDENTITY_ADMINISTRATOR);
        administratorRepository.add(identityAdministrator);

        UUID appliedApplicationId = UUID.randomUUID();
        appliedApplication = new RegistrationApplication(appliedApplicationId, "name", "brn", "sole", LocalDateTime.now());
        registrationRepository.add(appliedApplication);

        applicationAdministrator = new AdministerRegistrationApplication(registrationRepository, administrationRepository, administratorRepository, eventPublisher);
    }

    @Test
    public void adminRoleInvalid_shouldThrowException() {
        Administrator administrator = new Administrator(AdministratorRole.PROCUREMENT_ADMINISTRATOR);
        administratorRepository.add(administrator);

        ApplicationAdministration administration = new ApplicationAdministration(
                appliedApplication.applicationId(), AdministrationStatus.APPROVE,
                administrator.getAdministratorId(), null);

        RuntimeException error = Assert.assertThrows(InvalidRegistrationAdministration.class, ()-> applicationAdministrator.administer(administration));
        System.out.println(error.getMessage());
    }

    @Test
    public void administerRegistrationTwice_shouldThrowException() {
        RegistrationAdministration registrationAdministration = new RegistrationAdministration(
                appliedApplication.applicationId(), AdministrationStatus.REJECT,
                identityAdministrator.getAdministratorId(), null, LocalDateTime.now());
        administrationRepository.add(registrationAdministration);

        ApplicationAdministration administration = new ApplicationAdministration(
                appliedApplication.applicationId(), AdministrationStatus.APPROVE,
                identityAdministrator.getAdministratorId(), null);

        RuntimeException error = Assert.assertThrows(InvalidRegistrationAdministration.class, ()-> applicationAdministrator.administer(administration));
        System.out.println(error.getMessage());
    }
}
