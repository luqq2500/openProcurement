package registration.usecase;

import port.IntegrationEventPublisher;
import registration.RegistrationStatus;
import registration.api.RegistrationStatusUpdater;
import registration.event.RegistrationApproved;
import registration.event.RegistrationRejected;

import java.util.UUID;

public class UpdateRegistrationStatus implements RegistrationStatusUpdater {
    private final IntegrationEventPublisher approvedEventPublisher;
    private final IntegrationEventPublisher rejectedEventPublisher;
    public UpdateRegistrationStatus(IntegrationEventPublisher approvedEventPublisher, IntegrationEventPublisher rejectedEventPublisher) {
        this.approvedEventPublisher = approvedEventPublisher;
        this.rejectedEventPublisher = rejectedEventPublisher;
    }
    @Override
    public void update(UUID administratorId, UUID registrationId, RegistrationStatus status) {
        if (status.equals(RegistrationStatus.APPROVE)){
            approvedEventPublisher.publish(new RegistrationApproved(registrationId, administratorId));}
        if (status.equals(RegistrationStatus.REJECT)){
            rejectedEventPublisher.publish(new RegistrationRejected(registrationId, administratorId));}
    }
}
