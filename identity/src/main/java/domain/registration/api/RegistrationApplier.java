package domain.registration.api;

import domain.registration.RegistrationApplierRequest;

public interface RegistrationApplier {
    void apply(RegistrationApplierRequest registrationRequest);
}
