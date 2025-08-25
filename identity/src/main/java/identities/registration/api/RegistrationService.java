package identities.registration.api;

import identities.registration.ApplyRegistrationDetails;

import java.util.UUID;

public interface RegistrationService {
    void request(UUID guessAccountId);
    void apply(ApplyRegistrationDetails registrationRequest);
}
