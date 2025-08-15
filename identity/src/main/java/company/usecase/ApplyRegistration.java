package company.usecase;

import annotation.DomainService;
import company.RegistrationApplication;
import company.RegistrationRequest;
import company.address.Address;
import company.api.RegistrationApplier;
import company.events.integrationEvents.RegistrationSubmitted;
import company.exception.InvalidRegistrationSubmission;
import company.spi.RegistrationApplicationRepository;
import company.spi.RegistrationRequestRepository;
import port.IntegrationEventPublisher;

@DomainService
public class ApplyRegistration implements RegistrationApplier {
    private final RegistrationApplicationRepository applicationRepository;
    private final RegistrationRequestRepository requestRepository;
    private final IntegrationEventPublisher<RegistrationSubmitted> integrationEventPublisher;
    public ApplyRegistration(RegistrationApplicationRepository applicationRepository, RegistrationRequestRepository requestRepository, IntegrationEventPublisher<RegistrationSubmitted> integrationEventPublisher) {
        this.applicationRepository = applicationRepository;
        this.requestRepository = requestRepository;
        this.integrationEventPublisher = integrationEventPublisher;
    }
    @Override
    public void apply(ApplyRegistrationRequest request) {
        RegistrationRequest registrationRequest = requestRepository.getById(request.registrationRequestId());
        if (!registrationRequest.isValid()){
            throw new InvalidRegistrationSubmission("Registration request has already expired.");
        }
        RegistrationApplication application = new RegistrationApplication(
                registrationRequest.getEmail(),
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
        integrationEventPublisher.publish(new RegistrationSubmitted(application));
    }
}
