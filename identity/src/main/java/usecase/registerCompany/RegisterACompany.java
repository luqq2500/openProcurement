package usecase.registerCompany;

import annotation.DomainService;
import identities.registration.*;
import identities.registration.api.RegistrationService;
import identities.registration.events.RegistrationSubmitted;
import identities.registration.spi.RegistrationAdministrationRepository;
import identities.registration.spi.RegistrationRepository;
import identities.registration.spi.RegistrationRequestRepository;
import port.IntegrationEventPublisher;
import usecase.registerCompany.exception.InvalidCompanyRegistration;

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
public class RegisterACompany implements RegistrationService {
    private final RegistrationRepository registrationRepository;
    private final RegistrationRequestRepository requestRepository;
    private final RegistrationAdministrationRepository administrationRepository;
    private final IntegrationEventPublisher<RegistrationSubmitted> eventPublisher;

    public RegisterACompany(RegistrationRepository registrationRepository, RegistrationRequestRepository requestRepository, RegistrationAdministrationRepository administrationRepository, IntegrationEventPublisher<RegistrationSubmitted> eventPublisher) {
        this.registrationRepository = registrationRepository;
        this.requestRepository = requestRepository;
        this.administrationRepository = administrationRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void request(UUID guessAccountId) {
        requestRepository.add(new RegistrationRequest(guessAccountId));
    }

    @Override
    public void apply(ApplyRegistrationDetails application) {
        validateUniqueness(application);
        Optional<RegistrationApplication> registration = validateApplicationAdministration(application);

        RegistrationApplication registrationApplication;
        CompanyDetails companyDetails = new CompanyDetails(application.companyName(), application.address(), application.brn(), application.structure());
        AccountAdminDetails accountAdminDetails = new AccountAdminDetails(application.firstName(), application.lastName(), application.email(), application.password());

        if (registration.isPresent() && administrationRepository.findLatest(registration.get().applicationId()).get().applicableForResubmit()){
            registrationApplication = registration.get().resubmit(companyDetails, accountAdminDetails);}
        else{
            registrationApplication = new RegistrationApplication(
                    application.requestId(), UUID.randomUUID(),
                    companyDetails, accountAdminDetails, LocalDateTime.now());}

        registrationRepository.add(registrationApplication);
        eventPublisher.publish(new RegistrationSubmitted(registrationApplication));
    }

    private Optional<RegistrationApplication> validateApplicationAdministration(ApplyRegistrationDetails application) {
        Optional<RegistrationApplication> registration = registrationRepository.findLatest(application.requestId());
        Optional<RegistrationAdministration> administration;
        if (registration.isPresent()) {
            administration = administrationRepository.findLatest(application.requestId());
            if (administration.isEmpty()) {
                throw new InvalidCompanyRegistration("Application is not yet administered.");}
            if (administration.get().isApproved()) {
                throw new InvalidCompanyRegistration("Application is already approved.");}}
        return registration;
    }

    private void validateUniqueness(ApplyRegistrationDetails application) {
        Optional<RegistrationApplication> findUsedCompanyNameInRegistration = registrationRepository.findLatestByCompanyName(application.companyName());
        if (findUsedCompanyNameInRegistration.isPresent()){
            RegistrationApplication registration = findUsedCompanyNameInRegistration.get();
            if (registration.getCompanyName().equals(application.companyName())
                    && administrationRepository.get(registration.applicationId()).isApproved()){
                throw new InvalidCompanyRegistration(application.companyName() + " is already been used.");}}

        Optional<RegistrationApplication> findUsedBrnInRegistration = registrationRepository.findLatestByBrn(application.brn());
        if (findUsedBrnInRegistration.isPresent()){
            RegistrationApplication registration = findUsedBrnInRegistration.get();
            if (registration.getBrn().equals(application.brn())
            && administrationRepository.get(registration.applicationId()).isApproved()){
                throw new InvalidCompanyRegistration(application.brn() + " is already been used.");}}

        Optional<RegistrationApplication> findUsedEmail = registrationRepository.findLatestByEmail(application.email());
        if (findUsedEmail.isPresent() && administrationRepository.get(findUsedEmail.get().applicationId()).isApproved()){
            throw new InvalidCompanyRegistration(application.email() + " is already been used.");}
    }
}
