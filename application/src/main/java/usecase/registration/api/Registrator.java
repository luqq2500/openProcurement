package usecase.registration.api;

import usecase.registration.ApplyRegistrationDetails;

import java.util.UUID;

public interface Registrator {
    void request(UUID guessAccountId);
    void apply(ApplyRegistrationDetails registrationRequest);
}
