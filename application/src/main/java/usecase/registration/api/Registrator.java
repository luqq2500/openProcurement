package usecase.registration.api;

import usecase.registration.RegistrationDetails;

import java.util.UUID;

public interface Registrator {
    void request(UUID guessAccountId);
    void apply(RegistrationDetails registrationRequest);
}
