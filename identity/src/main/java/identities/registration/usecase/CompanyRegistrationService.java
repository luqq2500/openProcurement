package identities.registration.usecase;

import annotation.DomainService;
import identities.employee.Employee;
import identities.employee.spi.EmployeeRepository;
import identities.registration.*;
import identities.registration.api.RegistrationService;
import identities.registration.events.RegistrationSubmitted;
import identities.registration.exception.InvalidRegistrationApplication;
import identities.registration.spi.RegistrationRepository;
import identities.registration.spi.RegistrationRequestRepository;
import port.IntegrationEventPublisher;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/// ApplyRegistration: Company registration application use case.
///
/// 1. Check submitted information violates uniqueness: brn, company name, and username.
/// 2. Check submitted information is new or an update from previous rejected application.
/// 3. Save application.
/// 4. Publish registration submitted event
///
/// Params:
/// @ requestId: reference to registration request
/// @ companyDetails: name, address, brn, structure
/// @ administratorAccountDetails: first name, last name, username, password
///

@DomainService
public class CompanyRegistrationService implements RegistrationService {
    private final RegistrationRepository registrationRepository;
    private final RegistrationRequestRepository registrationRequestRepository;
    private final EmployeeRepository employeeRepository;
    private final IntegrationEventPublisher<RegistrationSubmitted> integrationEventPublisher;

    public CompanyRegistrationService(RegistrationRepository registrationRepository, RegistrationRequestRepository registrationRequestRepository, EmployeeRepository employeeRepository, IntegrationEventPublisher<RegistrationSubmitted> integrationEventPublisher) {
        this.registrationRepository = registrationRepository;
        this.registrationRequestRepository = registrationRequestRepository;
        this.employeeRepository = employeeRepository;
        this.integrationEventPublisher = integrationEventPublisher;
    }

    @Override
    public void request(UUID guessAccountId) {
        registrationRequestRepository.add(new RegistrationRequest(guessAccountId));
    }

    @Override
    public void apply(ApplyRegistrationDetails registration) {
        validateUniqueness(registration);
        Optional<RegistrationApplication> findRequestId = validateExistingRegistration(registration);

        CompanyDetails companyDetails = new CompanyDetails(registration.companyName(), registration.address(), registration.brn(), registration.structure());
        AccountAdministratorDetails accountAdministratorDetails = new AccountAdministratorDetails(registration.firstName(), registration.lastName(), registration.email(), registration.password());
        RegistrationApplication application;

        if (findRequestId.isPresent() && findRequestId.get().isRejected()){
            application = findRequestId.get().changeDetails(companyDetails, accountAdministratorDetails);}
        else {
            RegistrationRequest request = registrationRequestRepository.getById(registration.requestId());
            application = new RegistrationApplication(request.getId(),
                    companyDetails, accountAdministratorDetails,
                    RegistrationStatus.UNDER_REVIEW, LocalDateTime.now(),
                    1, null);}

        registrationRepository.add(application);
        integrationEventPublisher.publish(new RegistrationSubmitted(application));
    }

    private Optional<RegistrationApplication> validateExistingRegistration(ApplyRegistrationDetails registration) {
        Optional<RegistrationApplication> findRequestId = registrationRepository.findLatest(registration.requestId());
        if (findRequestId.isPresent() && findRequestId.get().isApproved()){
            throw new InvalidRegistrationApplication("Registration has been approved.");}
        if (findRequestId.isPresent() && findRequestId.get().isUnderReview()){
            throw new InvalidRegistrationApplication("Registration is under review.");}
        return findRequestId;
    }

    private void validateUniqueness(ApplyRegistrationDetails registration) {
        Optional<RegistrationApplication> findCompanyName = registrationRepository.findLatestByCompanyName(registration.companyName());
        Optional<RegistrationApplication> findBrn = registrationRepository.findLatestByBrn(registration.brn());
        Optional<Employee> findEmail = employeeRepository.findByEmail(registration.email());
        if (findCompanyName.isPresent() && !findCompanyName.get().isRejected()){
            throw new InvalidRegistrationApplication("Company name is already in use.");}
        if (findBrn.isPresent() && !findBrn.get().isRejected()){
            throw new InvalidRegistrationApplication("Brn is already in use.");}
        if (findEmail.isPresent() && findEmail.get().isActive()){
            throw new InvalidRegistrationApplication("Email is already in use.");}
    }
}
