package usecase;

import administrator.Administrator;
import administrator.spi.AdministratorRepository;
import port.IntegrationEventPublisher;
import identity.registration.SubmittedRegistration;
import identity.registration.RegistrationStatus;
import identity.registration.api.RegistrationStatusUpdater;
import identity.registration.event.RegistrationAdministered;
import identity.registration.spi.SubmittedRegistrationRepository;

import java.util.UUID;

public class UpdateRegistrationStatus implements RegistrationStatusUpdater {
    private final SubmittedRegistrationRepository submittedRegistrationRepository;
    private final AdministratorRepository administratorRepository;
    private final IntegrationEventPublisher registrationAdministeredEventPublisher;
    public UpdateRegistrationStatus(SubmittedRegistrationRepository submittedRegistrationRepository, AdministratorRepository administratorRepository, IntegrationEventPublisher<RegistrationAdministered> registrationAdministeredEventPublisher) {
        this.submittedRegistrationRepository = submittedRegistrationRepository;
        this.administratorRepository = administratorRepository;
        this.registrationAdministeredEventPublisher = registrationAdministeredEventPublisher;
    }
    @Override
    public void update(UUID administratorId, UUID registrationId, RegistrationStatus status) {
        SubmittedRegistration submittedRegistration = submittedRegistrationRepository.get(registrationId);
        Administrator administrator = administratorRepository.get(administratorId);
        SubmittedRegistration updatedSubmittedRegistration = submittedRegistration.updateStatus(administrator, status);
        submittedRegistrationRepository.add(updatedSubmittedRegistration);

        registrationAdministeredEventPublisher.publish(new RegistrationAdministered(updatedSubmittedRegistration));
    }
}
