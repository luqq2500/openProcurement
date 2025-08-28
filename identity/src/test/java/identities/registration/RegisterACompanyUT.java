package identities.registration;

import event.DomainEventHandler;
import event.EventBus;
import event.InMemoryEventBus;
import identities.address.Address;
import identities.address.Country;
import identities.company.Structure;
import usecase.registerCompany.events.MockRegistrationSubmittedPublisher;
import identities.registration.spi.*;
import identities.registration.api.RegistrationService;
import usecase.registerCompany.events.RegistrationSubmitted;
import usecase.registerCompany.events.RegistrationSubmittedHandler;
import usecase.registerCompany.events.integration.RegistrationSubmittedIntegration;
import usecase.registerCompany.ApplyRegistrationDetails;
import usecase.registerCompany.exception.InvalidCompanyRegistration;
import usecase.registerCompany.RegisterACompany;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import port.IntegrationEventPublisher;

import java.time.LocalDateTime;
import java.util.UUID;

public class RegisterACompanyUT {
    private RegistrationService registrationService;
    private RegistrationRequestRepository requestRepository;
    private RegistrationAdministrationRepository administrationRepository;
    private RegistrationApplication appliedRegistration;
    private UUID requestId;

    @Before
    public void setUp() throws Exception {
        RegistrationRepository registrationRepository = new InMemoryRegistrationRepository();
        requestRepository = new InMemoryRegistrationRequestRepository();
        administrationRepository = new InMemoryRegistrationAdministrationRepository();
        EventBus eventBus = InMemoryEventBus.INSTANCE;

        IntegrationEventPublisher<RegistrationSubmittedIntegration> integrationEventPublisher = new MockRegistrationSubmittedPublisher();
        DomainEventHandler<RegistrationSubmitted> registrationSubmittedHandler = new RegistrationSubmittedHandler(integrationEventPublisher);

        eventBus.subscribe(registrationSubmittedHandler);
        registrationService = new RegisterACompany(registrationRepository, requestRepository, administrationRepository);

        UUID guessId = UUID.randomUUID();
        requestId = UUID.randomUUID();
        UUID applicationId = UUID.randomUUID();
        RegistrationRequest registrationRequested = new RegistrationRequest(guessId);
        requestRepository.add(registrationRequested);
        appliedRegistration = new RegistrationApplication(registrationRequested.getId(), applicationId,
                new CompanyDetails("1", new Address("1", "1", "1", "1", "43900", "sepang", Country.MALAYSIA),
                        "222", Structure.SOLE),
                new AccountAdminDetails("luqman", "hakim", "email", "***"),
                LocalDateTime.now());
        registrationRepository.add(appliedRegistration);
    }

    @Test
    public void companyNameApproved_shouldThrowException(){
        RegistrationAdministration administration = new RegistrationAdministration(appliedRegistration.applicationId(), RegistrationStatus.APPROVED, LocalDateTime.now());
        administrationRepository.add(administration);

        ApplyRegistrationDetails request = new ApplyRegistrationDetails(
                requestId, appliedRegistration.getCompanyName(),
                new Address("1", "1", "1", "1", "1", "1", Country.MALAYSIA),
                "22222", Structure.SOLE,
                "luqman", "hakim",
                "hakimluqq25@gmail.com", "123"
        );
        InvalidCompanyRegistration exception = Assert.assertThrows(InvalidCompanyRegistration.class, ()-> registrationService.apply(request));
        System.out.println(exception.getMessage());
    }
    @Test
    public void brnApproved_shouldThrowException() {
        RegistrationAdministration administration = new RegistrationAdministration(appliedRegistration.applicationId(), RegistrationStatus.APPROVED, LocalDateTime.now());
        administrationRepository.add(administration);
        ApplyRegistrationDetails request = new ApplyRegistrationDetails(
                requestId, "Lexus",
                new Address("1", "1", "1", "1", "1", "1", Country.MALAYSIA),
                appliedRegistration.getBrn(), Structure.SOLE,
                "luqman", "hakim",
                "hakimluqq25@gmail.com", "123"
        );
        InvalidCompanyRegistration exception = Assert.assertThrows(InvalidCompanyRegistration.class, () -> registrationService.apply(request));
        System.out.println(exception.getMessage());
    }

    @Test
    public void emailApproved_shouldThrowException(){
        RegistrationAdministration administration = new RegistrationAdministration(appliedRegistration.applicationId(), RegistrationStatus.APPROVED, LocalDateTime.now());
        administrationRepository.add(administration);
        ApplyRegistrationDetails request = new ApplyRegistrationDetails(
                requestId, "Lexus",
                new Address("1", "1", "1", "1", "1", "1", Country.MALAYSIA),
                "2222", Structure.SOLE,
                "luqman", "hakim",
                appliedRegistration.getEmail(), "123"
        );
        InvalidCompanyRegistration exception = Assert.assertThrows(InvalidCompanyRegistration.class, ()-> registrationService.apply(request));
        System.out.println(exception.getMessage());
    }

    @Test
    public void requestApproved_shouldThrowException(){
        RegistrationAdministration administration = new RegistrationAdministration(appliedRegistration.applicationId(), RegistrationStatus.APPROVED, LocalDateTime.now());
        administrationRepository.add(administration);

        ApplyRegistrationDetails request = new ApplyRegistrationDetails(
                appliedRegistration.getRequestId(), "terraForm",
                new Address("1", "1", "1", "1", "1", "1", Country.MALAYSIA),
                "22222", Structure.SOLE,
                "luqman", "hakim",
                "hakimluqq25@gmail.com", "123"
        );
        InvalidCompanyRegistration exception = Assert.assertThrows(InvalidCompanyRegistration.class, ()-> registrationService.apply(request));
        System.out.println(exception.getMessage());
    }

    @Test
    public void registrationNotYetAdministered_shouldThrowException(){
        ApplyRegistrationDetails request = new ApplyRegistrationDetails(
                appliedRegistration.getRequestId(),
                "terra",
                new Address("1", "1", "1", "1", "1", "1", Country.MALAYSIA),
                "22222", Structure.SOLE,
                "luqman", "hakim",
                "hakimluqq25@gmail.com", "123"
        );
        InvalidCompanyRegistration exception = Assert.assertThrows(InvalidCompanyRegistration.class, ()-> registrationService.apply(request));
        System.out.println(exception.getMessage());
    }

    @Test
    public void newRegistration_shouldNotThrowException(){
        RegistrationRequest registrationRequest = new RegistrationRequest(UUID.randomUUID());
        requestRepository.add(registrationRequest);
        ApplyRegistrationDetails request = new ApplyRegistrationDetails(
                registrationRequest.getId(),
                "terra",
                new Address("1", "1", "1", "1", "1", "1", Country.MALAYSIA),
                "22222", Structure.SOLE,
                "luqman", "hakim",
                "hakimluqq25@gmail.com", "123"
        );
        registrationService.apply(request);
    }
}
