package usecase;

import administrator.Administrator;
import administrator.spi.AdministratorRepository;
import port.IntegrationEventPublisher;
import registration.Registration;
import registration.RegistrationStatus;
import registration.api.RegistrationStatusUpdater;
import registration.event.RegistrationApproved;
import registration.event.RegistrationRejected;
import registration.spi.RegistrationRepository;

import java.util.UUID;

public class UpdateRegistrationStatus implements RegistrationStatusUpdater {
    private final RegistrationRepository registrationRepository;
    private final AdministratorRepository administratorRepository;
    private final IntegrationEventPublisher approvedEventPublisher;
    private final IntegrationEventPublisher rejectedEventPublisher;
    public UpdateRegistrationStatus(RegistrationRepository registrationRepository, AdministratorRepository administratorRepository, IntegrationEventPublisher approvedEventPublisher, IntegrationEventPublisher rejectedEventPublisher) {
        this.registrationRepository = registrationRepository;
        this.administratorRepository = administratorRepository;
        this.approvedEventPublisher = approvedEventPublisher;
        this.rejectedEventPublisher = rejectedEventPublisher;
    }
    @Override
    public void update(UUID administratorId, UUID registrationId, RegistrationStatus status) {
        Registration registration = registrationRepository.get(registrationId);
        Administrator administrator = administratorRepository.get(administratorId);
        Registration updatedRegistration = registration.updateStatus(administrator, status);
        registrationRepository.add(updatedRegistration);
        if (registration.isApproved()){
            approvedEventPublisher.publish(new RegistrationApproved(registrationId, administratorId));}
        if (registration.isRejected()){
            rejectedEventPublisher.publish(new RegistrationRejected(registrationId, administratorId));}
    }
}
