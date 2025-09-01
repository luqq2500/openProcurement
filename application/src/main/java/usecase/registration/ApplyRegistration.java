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

/// ApplyRegistration: Company registration application use case.
///
/// 1. Check submitted information violates uniqueness: brn, company name, and email.
/// 2. Check submitted information is new or an update from previous rejected application.
/// 3. Save application.
/// 4. Publish registration submitted event
///
/// Params:
/// @ requestId: reference to registration request
/// @ companyDetails: name, address, brn, structure
/// @ administratorAccountDetails: first name, last name, email, password
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

    @Override
    public void request(UUID guessAccountId) {
        RegistrationRequest request = new RegistrationRequest(guessAccountId);
        requestRepository.add(request);
        eventBus.publish(new RegistrationRequested(request));
    }

    @Override
    public void apply(ApplyRegistrationDetails application) {
        validateCompanyName(application);
        validateBrn(application);
        validateEmail(application);
        Optional<Registration> registration = validateExistingRequest(application);

        Registration registrationApplication;
        CompanyDetails companyDetails = new CompanyDetails(application.companyName(), application.address(), application.brn(), application.structure());
        AccountAdminDetails accountAdminDetails = new AccountAdminDetails(application.firstName(), application.lastName(), application.email(), application.password());

        if (registration.isPresent() && administrationRepository.get(registration.get().getApplicationId()).applicableForResubmit()){
            registrationApplication = registration.get().resubmit(companyDetails, accountAdminDetails);}
        else{
            registrationApplication = new Registration(application.requestId(),
                    UUID.randomUUID(), companyDetails, accountAdminDetails, LocalDateTime.now());}

        registrationRepository.add(registrationApplication);
        eventBus.publish(new RegistrationSubmitted(registrationApplication));
    }

    private void validateEmail(ApplyRegistrationDetails application) {
        Optional<Registration> findUsedEmail = registrationRepository.findLatestByEmail(application.email());
        if (findUsedEmail.isPresent() && administrationRepository.get(findUsedEmail.get().applicationId()).isApproved()){
            throw new InvalidCompanyRegistration(application.email() + " is already been used.");}
    }

    private void validateBrn(ApplyRegistrationDetails application) {
        Optional<Registration> findUsedBrnInRegistration = registrationRepository.findLatestByBrn(application.brn());
        if (findUsedBrnInRegistration.isPresent()){
            Registration registration = findUsedBrnInRegistration.get();
            if (registration.getBrn().equals(application.brn())
                    && administrationRepository.get(registration.applicationId()).isApproved()){
                throw new InvalidCompanyRegistration(application.brn() + " is already been used.");}}
    }

    private void validateCompanyName(ApplyRegistrationDetails application) {
        Optional<Registration> findUsedCompanyNameInRegistration = registrationRepository.findLatestByCompanyName(application.companyName());
        if (findUsedCompanyNameInRegistration.isPresent()){
            Registration registration = findUsedCompanyNameInRegistration.get();
            if (registration.getCompanyName().equals(application.companyName())
                    && administrationRepository.get(registration.applicationId()).isApproved()){
                throw new InvalidCompanyRegistration(application.companyName() + " is already been used.");}}
    }

    private Optional<Registration> validateExistingRequest(ApplyRegistrationDetails application) {
        Optional<Registration> registration = registrationRepository.findLatest(application.requestId());
        Optional<RegistrationAdministration> administration;
        if (registration.isPresent()) {
            administration = administrationRepository.find(registration.get().getApplicationId());
            if (administration.isEmpty()) {
                throw new InvalidCompanyRegistration("Application is not yet administered.");}
            if (administration.get().isApproved()) {
                throw new InvalidCompanyRegistration("Application is already approved.");}}
        return registration;
    }
}
