package company.usecase;

import company.Company;
import company.RegistrationApplication;
import company.RegistrationRequest;
import company.address.Address;
import company.api.RegistrationApplier;
import company.exception.InvalidRegistrationApplication;
import company.spi.CompanyRepository;
import company.spi.RegistrationApplicationRepository;
import company.spi.RegistrationRequestRepository;

import java.util.Optional;

public class ApplyRegistration implements RegistrationApplier {
    private final RegistrationApplicationRepository applicationRepository;
    private final RegistrationRequestRepository requestRepository;
    private final CompanyRepository companyRepository;
    public ApplyRegistration(RegistrationApplicationRepository applicationRepository, RegistrationRequestRepository requestRepository, CompanyRepository companyRepository) {
        this.applicationRepository = applicationRepository;
        this.requestRepository = requestRepository;
        this.companyRepository = companyRepository;
    }
    @Override
    public void apply(ApplyRegistrationRequest request) {
        RegistrationRequest registrationRequest = requestRepository.getById(request.registrationRequestId());
        if (!registrationRequest.isValid()){
            throw new InvalidRegistrationApplication("Registration request has already expired.");
        }
        Optional<RegistrationApplication> getApplication = applicationRepository.getByBrn(request.brn());
        if (getApplication.isPresent() && getApplication.get().isUnderReview()) {
            throw new InvalidRegistrationApplication("Registration application is under review.");
        }
        Optional<Company> company = companyRepository.findByBrn(request.brn());
        if (company.isPresent() && company.get().isActive()) {
            throw new InvalidRegistrationApplication("Business has been registered.");
        }
        RegistrationApplication application = new RegistrationApplication(
                request.companyName(),
                new Address(request.street1(), request.street2(), request.street3(), request.city(), request.zip(), request.state(), request.country()),
                request.brn(),
                request.structure(),
                request.taxNumber(),
                request.firstName(),
                request.lastName(),
                request.username() == null ? registrationRequest.getEmail() : request.username(),
                request.password()
        );
        applicationRepository.save(application);
    }
}
