package domain.registration.usecase;

import annotation.DomainService;
import domain.registration.AccountAdministratorDetails;
import domain.registration.CompanyDetails;
import domain.registration.RegistrationApplication;
import domain.registration.RegistrationApplicationRequest;
import domain.registration.api.RegistrationApplier;
import domain.registration.events.domain.RegistrationApplicationSubmitted;
import domain.registration.exception.InvalidRegistrationApplication;
import domain.registration.spi.RegistrationRepository;
import domain.registration.spi.RegistrationRequestRepository;
import domain.registration.usecase.dto.RegistrationApplierRequest;
import event.EventBus;

@DomainService
public class ApplyRegistration implements RegistrationApplier {
    private final RegistrationRequestRepository requestRepository;
    private final RegistrationRepository registrationRepository;
    private final EventBus eventBus;

    public ApplyRegistration(RegistrationRequestRepository requestRepository, RegistrationRepository registrationRepository, EventBus eventBus) {
        this.requestRepository = requestRepository;
        this.registrationRepository = registrationRepository;
        this.eventBus = eventBus;
    }

    @Override
    public void apply(RegistrationApplierRequest registration) {
        RegistrationApplicationRequest request = requestRepository.getById(registration.requestId());
        if (!request.isValid()){
            throw new InvalidRegistrationApplication("Registration request has expired.");
        }
        RegistrationApplication registrationApplication = new RegistrationApplication(
                request.getId(),
                new CompanyDetails(registration.companyName(), registration.address(), registration.brn(), registration.structure()),
                new AccountAdministratorDetails(registration.firstName(), registration.lastName(), registration.username() == null ? request.getEmail() : registration.username(), registration.password())
        );
        registrationRepository.add(registrationApplication);
        eventBus.getInstance().publish(new RegistrationApplicationSubmitted(registrationApplication));
    }
}
