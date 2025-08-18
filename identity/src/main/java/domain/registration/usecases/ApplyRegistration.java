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
        RegistrationRequest request = registrationRequestRepository.getById(registration.requestId());
        RegistrationApplication registrationApplication = new RegistrationApplication(
                request.getId(),
                new CompanyDetails(registration.companyName(), registration.address(), registration.brn(), registration.structure()),
                new AccountAdministratorDetails(registration.firstName(), registration.lastName(), registration.username(), registration.password())
        );
        registrationRepository.add(registrationApplication);
        eventBus.getInstance().publish(new RegistrationSubmitted(registrationApplication));
    }
}
