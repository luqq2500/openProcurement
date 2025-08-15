package sys;

import company.RegistrationApplication;
import company.exception.RegistrationApplicationException;
import company.spi.RegistrationApplicationRepository;

import java.util.Optional;
import java.util.UUID;

public class PreValidateCompanyRegistration implements CompanyRegistrationPreValidator {
    private final RegistrationApplicationRepository applicationRepository;

    public PreValidateCompanyRegistration(RegistrationApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    public void preValidate(UUID registrationId, String brn, String taxNumber) {
    }
}
