package company.spi;

import company.registration.RegistrationPreValidation;

public interface NotPassedPreValidationRegistrationRepository {
    void add(RegistrationPreValidation preValidation);
}
