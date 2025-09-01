package usecase.registration;

import annotation.DomainService;
import applications.registration.*;
import usecase.registration.api.Registrator;
import event.EventBus;
import usecase.registration.events.RegistrationRequested;
import usecase.registration.events.RegistrationSubmitted;
import applications.registration.spi.RegistrationAdministrationRepository;
import applications.registration.spi.RegistrationRepository;
import applications.registration.spi.RegistrationRequestRepository;
import usecase.registration.exception.InvalidCompanyRegistration;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

///
/// Registration application use case.
///

@DomainService
public class ApplyRegistration implements Registrator {
    private final RegistrationRepository registrationRepository;
    private final RegistrationRequestRepository requestRepository;
    private final RegistrationAdministrationRepository administrationRepository;
    private final EventBus eventBus;

    public ApplyRegistration(RegistrationRepository registrationRepository, RegistrationRequestRepository requestRepository, RegistrationAdministrationRepository administrationRepository, EventBus eventBus) {
        this.registrationRepository = registrationRepository;
        this.requestRepository = requestRepository;
        this.administrationRepository = administrationRepository;
        this.eventBus = eventBus;
    }

    /// Method to request for registration application.
    /// Params: @guessId: Guess account id.
    @Override
    public void request(UUID guessId) {
        RegistrationRequest request = new RegistrationRequest(guessId);
        requestRepository.add(request);
        eventBus.publish(new RegistrationRequested(request));
    }

    /// Method to apply for registration application.
    /// Params:
    /// @registrationDetails:
    ///     1. requestId: Requested registration application id.
    ///     2. companyName: Legal company name.
    ///     3. address: Legal company address.
    ///     4. brn: Legal business registration number.
    ///     5. structure: Legal business structure.
    ///     6. firstName: Account administrator first name.
    ///     7. lastName: Account administrator last name.
    ///     8. email: Account administrator email.
    ///     9. password: Account administrator password.
    @Override
    public void apply(RegistrationDetails details) {
        Registration application;
        CompanyDetails companyDetails = new CompanyDetails(details.companyName(), details.address(), details.brn(), details.structure());
        AccountAdminDetails accountAdminDetails = new AccountAdminDetails(details.firstName(), details.lastName(), details.email(), details.password());

        // validate information uniqueness: company name, business registration number, email.
        validateCompanyName(details);
        validateBrn(details);
        validateEmail(details);

        // validate existing registration application
        Optional<Registration> registrationResubmit = validateExistingRequest(details);

        // (submit/resubmit) application
        application = registrationResubmit.map(registration -> registration.resubmit(companyDetails, accountAdminDetails)).orElseGet(() -> new Registration(details.requestId(),
                UUID.randomUUID(), companyDetails, accountAdminDetails, LocalDateTime.now()));
        registrationRepository.add(application);
        eventBus.publish(new RegistrationSubmitted(application));
    }

    private void validateEmail(RegistrationDetails application) {
        Optional<Registration> findUsedEmail = registrationRepository.findLatestByEmail(application.email());
        if (findUsedEmail.isPresent() && administrationRepository.get(findUsedEmail.get().applicationId()).isApproved()){
            throw new InvalidCompanyRegistration(application.email() + " is already been used.");}
    }

    private void validateBrn(RegistrationDetails application) {
        Optional<Registration> findUsedBrnInRegistration = registrationRepository.findLatestByBrn(application.brn());
        if (findUsedBrnInRegistration.isPresent()){
            Registration registration = findUsedBrnInRegistration.get();
            if (registration.getBrn().equals(application.brn())
                    && administrationRepository.get(registration.applicationId()).isApproved()){
                throw new InvalidCompanyRegistration(application.brn() + " is already been used.");}}
    }

    private void validateCompanyName(RegistrationDetails application) {
        Optional<Registration> findUsedCompanyNameInRegistration = registrationRepository.findLatestByCompanyName(application.companyName());
        if (findUsedCompanyNameInRegistration.isPresent()){
            Registration registration = findUsedCompanyNameInRegistration.get();
            if (registration.getCompanyName().equals(application.companyName())
                    && administrationRepository.get(registration.applicationId()).isApproved()){
                throw new InvalidCompanyRegistration(application.companyName() + " is already been used.");}}
    }

    private Optional<Registration> validateExistingRequest(RegistrationDetails application) {
        Optional<Registration> registration = registrationRepository.findLatest(application.requestId());
        Optional<RegistrationAdministration> administration;
        if (registration.isPresent()) {
            administration = administrationRepository.find(registration.get().getApplicationId());
            if (administration.isEmpty()) {
                throw new InvalidCompanyRegistration("Application is not yet administered.");}
            if (!administration.get().applicableForResubmit()){
                throw new InvalidCompanyRegistration("Application is not applicable to resubmit.");
            }
        }
        return registration;
    }
}
