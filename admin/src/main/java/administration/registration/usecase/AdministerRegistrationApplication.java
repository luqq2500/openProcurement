package administration.registration.usecase;

import administrator.AdministratorRole;
import administration.ApplicationAdministration;
import administrator.spi.AdministratorRepository;
import administration.registration.RegistrationAdministration;
import administration.registration.RegistrationApplication;
import administration.registration.event.RegistrationAdministered;
import administration.ApplicationAdministrator;
import administration.registration.spi.RegistrationRepository;
import port.IntegrationEventPublisher;
import administration.registration.spi.RegistrationAdministrationRepository;

public class AdministerRegistrationApplication implements ApplicationAdministrator {
    private final RegistrationRepository registrationRepository;
    private final RegistrationAdministrationRepository administrationRepository;
    private final AdministratorRepository administratorRepository;
    private final IntegrationEventPublisher<RegistrationAdministered> eventPublisher;
    private final AdministratorRole administrationRole = AdministratorRole.IDENTITY_ADMINISTRATOR;

    public AdministerRegistrationApplication(RegistrationRepository registrationRepository, RegistrationAdministrationRepository administrationRepository, AdministratorRepository administratorRepository, IntegrationEventPublisher<RegistrationAdministered> eventPublisher) {
        this.registrationRepository = registrationRepository;
        this.administrationRepository = administrationRepository;
        this.administratorRepository = administratorRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void administer(ApplicationAdministration administration) {
        administrator.Administrator administrator = administratorRepository.get(administration.administratorId());

        // validate administrator role
        if (!administrator.validateRole(administrationRole)){
            throw new InvalidRegistrationAdministration("Administrator role is invalid.");
        }
        // validate application has been administered or not
        if (administrationRepository.find(administration.applicationId()).isPresent()){
            throw new InvalidRegistrationAdministration("Registration application has been administered.");
        }

        RegistrationApplication application = registrationRepository.get(administration.applicationId());
        RegistrationAdministration registrationAdministration = administrator.administerRegistration(application, administration.status(), administration.message());

        administrationRepository.add(registrationAdministration);
        eventPublisher.publish(new RegistrationAdministered(registrationAdministration));

    }
}
