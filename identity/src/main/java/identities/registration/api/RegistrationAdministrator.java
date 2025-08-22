package identities.registration.api;

import identities.registration.RegistrationStatus;

import java.util.UUID;

public interface RegistrationAdministrator {
    void administer(UUID administratorId, UUID registrationId, RegistrationStatus status);
}
