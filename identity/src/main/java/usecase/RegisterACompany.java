package usecase;

import annotation.DomainService;
import identities.employee.Employee;
import identities.employee.spi.EmployeeRepository;
import identities.registration.*;
import identities.registration.api.RegistrationService;
import identities.registration.events.RegistrationSubmitted;
import usecase.exception.InvalidCompanyRegistration;
import identities.registration.spi.RegistrationRepository;
import identities.registration.spi.RegistrationRequestRepository;
import port.IntegrationEventPublisher;

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
public class RegisterACompany implements RegistrationService {
    private final RegistrationRepository registrationRepository;
    private final RegistrationRequestRepository registrationRequestRepository;
    private final EmployeeRepository employeeRepository;
    private final IntegrationEventPublisher<RegistrationSubmitted> integrationEventPublisher;

    public RegisterACompany(RegistrationRepository registrationRepository, RegistrationRequestRepository registrationRequestRepository, EmployeeRepository employeeRepository, IntegrationEventPublisher<RegistrationSubmitted> integrationEventPublisher) {
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
        if (findRequestId.isPresent() && findRequestId.get().validForResubmit()){
           application = findRequestId.get();
           application.resubmit(companyDetails, accountAdministratorDetails);
        }
        else {
            RegistrationRequest request = registrationRequestRepository.getById(registration.requestId());
            application = new RegistrationApplication(request.getId(), companyDetails, accountAdministratorDetails);
            registrationRepository.add(application);
        }
        integrationEventPublisher.publish(new RegistrationSubmitted(application));
    }

    private Optional<RegistrationApplication> validateExistingRegistration(ApplyRegistrationDetails registration) {
        Optional<RegistrationApplication> findRequestId = registrationRepository.find(registration.requestId());
        if (findRequestId.isPresent() && findRequestId.get().isApproved()){
            throw new InvalidCompanyRegistration("Registration has been approved.");}
        if (findRequestId.isPresent() && findRequestId.get().isUnderReview()){
            throw new InvalidCompanyRegistration("Registration is under review.");}
        return findRequestId;
    }

    private void validateUniqueness(ApplyRegistrationDetails registration) {
        Optional<RegistrationApplication> findCompanyName = registrationRepository.findByCompanyName(registration.companyName());
        Optional<RegistrationApplication> findBrn = registrationRepository.findByBrn(registration.brn());
        Optional<Employee> findEmail = employeeRepository.findByEmail(registration.email());
        if (findCompanyName.isPresent() && !findCompanyName.get().isRejected()){
            throw new InvalidCompanyRegistration("Company name is already in use.");}
        if (findBrn.isPresent() && !findBrn.get().isRejected()){
            throw new InvalidCompanyRegistration("Brn is already in use.");}
        if (findEmail.isPresent() && findEmail.get().isActive()){
            throw new InvalidCompanyRegistration("Email is already in use.");}
    }
}
