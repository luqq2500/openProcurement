package registration.api;

import registration.RegistrationStatus;

import java.util.UUID;

public interface RegistrationStatusUpdater {
    void update(UUID administratorId, UUID registrationId, RegistrationStatus status);
}
