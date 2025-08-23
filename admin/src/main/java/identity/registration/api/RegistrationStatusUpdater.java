package identity.registration.api;

import identity.registration.RegistrationStatus;

import java.util.UUID;

public interface RegistrationStatusUpdater {
    void update(UUID administratorId, UUID registrationId, RegistrationStatus status);
}
