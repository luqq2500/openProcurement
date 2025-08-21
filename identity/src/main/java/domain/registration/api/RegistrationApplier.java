package domain.registration.api;

import domain.registration.ApplyRegistrationDetails;

public interface RegistrationApplier {
    void apply(ApplyRegistrationDetails registrationRequest);
}
