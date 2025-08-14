package company.api;

import company.usecase.ApplyRegistrationRequest;

public interface RegistrationApplier {
    void apply(ApplyRegistrationRequest request);
}
