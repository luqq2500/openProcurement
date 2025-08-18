package domain.registration.api;

import domain.registration.usecase.dto.RegistrationApplierRequest;

public interface RegistrationApplier {
    void apply(RegistrationApplierRequest registrationRequest);
}
