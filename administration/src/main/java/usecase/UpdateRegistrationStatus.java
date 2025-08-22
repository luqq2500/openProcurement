package usecase;

import administrator.Administrator;
import administrator.spi.AdministratorRepository;
import port.IntegrationEventPublisher;
import identity.registration.Registration;
import identity.registration.RegistrationStatus;
import identity.registration.api.RegistrationStatusUpdater;
import identity.registration.event.RegistrationAdministered;
import identity.registration.spi.RegistrationRepository;

import java.util.UUID;

public class UpdateRegistrationStatus implements RegistrationStatusUpdater {
    private final RegistrationRepository registrationRepository;
    private final AdministratorRepository administratorRepository;
    private final IntegrationEventPublisher registrationAdministeredEventPublisher;
    public UpdateRegistrationStatus(RegistrationRepository registrationRepository, AdministratorRepository administratorRepository, IntegrationEventPublisher<RegistrationAdministered> registrationAdministeredEventPublisher) {
        this.registrationRepository = registrationRepository;
        this.administratorRepository = administratorRepository;
        this.registrationAdministeredEventPublisher = registrationAdministeredEventPublisher;
    }
    @Override
    public void update(UUID administratorId, UUID registrationId, RegistrationStatus status) {
        Registration registration = registrationRepository.get(registrationId);
        Administrator administrator = administratorRepository.get(administratorId);
        Registration updatedRegistration = registration.updateStatus(administrator, status);
        registrationRepository.add(updatedRegistration);

        registrationAdministeredEventPublisher.publish(new RegistrationAdministered(updatedRegistration));
    }
}
