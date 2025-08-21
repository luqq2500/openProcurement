package domain.registration.api;

import domain.registration.ApplyRegistrationDetails;

public interface CompanyRegistrator {
    void apply(ApplyRegistrationDetails registrationRequest);
}
