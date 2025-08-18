package domain.registration.usecases;

import annotation.DomainService;
import domain.registration.AccountAdministratorDetails;
import domain.registration.CompanyDetails;
import domain.registration.RegistrationApplication;
import domain.registration.RegistrationApplierRequest;
import domain.registration.api.RegistrationApplier;
import domain.registration.events.RegistrationSubmitted;
import domain.registration.RegistrationRequest;
import domain.registration.spi.RegistrationRepository;
import domain.registration.spi.RegistrationRequestRepository;
import event.EventBus;

import java.util.Optional;

/// ApplyRegistration: Company registration application use case.
///
/// 1. Check submitted information is new or an update from previous rejected application.
/// 2. Save application.
/// 3. Publish registration submitted event
///
/// Params:
/// @ requestId: reference to registration request
/// @ companyDetails: name, address, brn, structure
/// @ administratorAccountDetails: first name, last name, username, password
///

@DomainService
public class ApplyRegistration implements RegistrationApplier {
    private final RegistrationRepository registrationRepository;
    private final RegistrationRequestRepository registrationRequestRepository;
    private final EventBus eventBus;

    public ApplyRegistration(RegistrationRepository registrationRepository, RegistrationRequestRepository registrationRequestRepository, EventBus eventBus) {
        this.registrationRepository = registrationRepository;
        this.registrationRequestRepository = registrationRequestRepository;
        this.eventBus = eventBus;
    }

    @Override
    public void apply(RegistrationApplierRequest registration) {
        RegistrationApplication application;
        CompanyDetails companyDetails = new CompanyDetails(registration.companyName(), registration.address(), registration.brn(), registration.structure());
        AccountAdministratorDetails accountAdministratorDetails = new AccountAdministratorDetails(registration.firstName(), registration.lastName(), registration.username(), registration.password());

        Optional<RegistrationApplication> previousRegistration = registrationRepository.findLatestByRequestId(registration.requestId());
        if (previousRegistration.isPresent() && previousRegistration.get().isRejected()){
            application = previousRegistration.get()
                    .updateCompanyDetails(companyDetails)
                    .updateAccountAdministratorDetails(accountAdministratorDetails)
                    .updateAll();
        } else {
            RegistrationRequest request = registrationRequestRepository.getById(registration.requestId());
            application = new RegistrationApplication(
                    request.getId(), companyDetails, accountAdministratorDetails);
        }
        registrationRepository.add(application);
        eventBus.publish(new RegistrationSubmitted(application));
    }
}
