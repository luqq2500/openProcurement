package identities.registration.api;

import identities.registration.ApplyRegistrationDetails;
import identities.registration.RegistrationStatus;

import java.util.UUID;

public interface RegistrationService {
    void request(UUID guessAccountId);
    void apply(ApplyRegistrationDetails registrationRequest);
    void administer(UUID administratorId, UUID registrationId, RegistrationStatus status);
}
