package company.spi;

import company.RegistrationPreValidation;

public interface NotPassedPreValidationRegistrationRepository {
    void add(RegistrationPreValidation preValidation);
}
