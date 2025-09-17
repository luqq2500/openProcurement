package usecase.registration.api;

import usecase.registration.dto.RegistrationDetails;

public interface RegistrationApplier {
    void apply(RegistrationDetails registrationRequest);
}
