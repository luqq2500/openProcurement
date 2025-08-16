package sys;

import company.registration.RegistrationApplication;
import company.registration.RegistrationPreValidation;
import company.spi.CompanyRepository;
import company.spi.NotPassedPreValidationRegistrationRepository;
import company.spi.RegistrationApplicationRepository;

import java.util.Optional;

public class PreValidateCompanyRegistration implements CompanyRegistrationPreValidator {
    private final NotPassedPreValidationRegistrationRepository preValidationRepository;
    private final RegistrationApplicationRepository applicationRepository;
    private final CompanyRepository companyRepository;

    public PreValidateCompanyRegistration(NotPassedPreValidationRegistrationRepository preValidationRepository, RegistrationApplicationRepository applicationRepository, CompanyRepository companyRepository) {
        this.preValidationRepository = preValidationRepository;
        this.applicationRepository = applicationRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public void preValidate(RegistrationPreValidation preValidation) {
        Optional<RegistrationApplication> application = applicationRepository.findByBrn(preValidation.brn());
        if (application.isPresent() && application.get().isInProgress()) {
            preValidationRepository.add(preValidation);
        }
    }
}
