package applications.registration;

import applications.registration.spi.RegistrationAdministrationRepository;
import applications.registration.spi.RegistrationRepository;
import applications.registration.spi.RegistrationRequestRepository;
import event.DomainEventHandler;
import event.EventBus;
import usecase.event.InMemoryEventBus;
import applications.address.Address;
import applications.address.Country;
import applications.registration.mock.InMemoryRegistrationAdministrationRepository;
import applications.registration.mock.InMemoryRegistrationRepository;
import applications.registration.mock.InMemoryRegistrationRequestRepository;
import usecase.registration.events.*;
import usecase.registration.api.Registrator;
import usecase.registration.events.integration.MockRegistrationSubmittedPublisher;
import usecase.registration.events.integration.RegistrationSubmitted_IE;
import usecase.registration.RegistrationDetails;
import usecase.registration.exception.InvalidCompanyRegistration;
import usecase.registration.ApplyRegistration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import port.IntegrationEventPublisher;

import java.time.LocalDateTime;
import java.util.UUID;

public class ApplyRegistrationUT {
    private Registrator registrator;
    private RegistrationRequestRepository requestRepository;
    private RegistrationAdministrationRepository administrationRepository;
    private Registration appliedRegistration;
    private UUID requestId;

    @Before
    public void setUp() throws Exception {
        RegistrationRepository registrationRepository = new InMemoryRegistrationRepository();
        requestRepository = new InMemoryRegistrationRequestRepository();
        administrationRepository = new InMemoryRegistrationAdministrationRepository();

        IntegrationEventPublisher<RegistrationSubmitted_IE> integrationEventPublisher = new MockRegistrationSubmittedPublisher();
        DomainEventHandler<RegistrationSubmitted> registrationSubmittedHandler = new RegistrationSubmittedHandler(integrationEventPublisher);
        DomainEventHandler<RegistrationRequested> registrationRequestedHandler = new RegistrationRequestedHandler();
        EventBus eventBus = InMemoryEventBus.INSTANCE;
        eventBus.subscribe(registrationSubmittedHandler);
        eventBus.subscribe(registrationRequestedHandler);

        registrator = new ApplyRegistration(registrationRepository, requestRepository, administrationRepository, eventBus);

        UUID guessId = UUID.randomUUID();
        requestId = UUID.randomUUID();
        UUID applicationId = UUID.randomUUID();
        RegistrationRequest registrationRequested = new RegistrationRequest(guessId);
        requestRepository.add(registrationRequested);
        appliedRegistration = new Registration(registrationRequested.getId(), applicationId,
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

        RegistrationDetails request = new RegistrationDetails(
                requestId, appliedRegistration.getCompanyName(),
                new Address("1", "1", "1", "1", "1", "1", Country.MALAYSIA),
                "22222", Structure.SOLE,
                "luqman", "hakim",
                "hakimluqq25@gmail.com", "123"
        );
        InvalidCompanyRegistration exception = Assert.assertThrows(InvalidCompanyRegistration.class, ()-> registrator.apply(request));
        System.out.println(exception.getMessage());
    }
    @Test
    public void brnApproved_shouldThrowException() {
        RegistrationAdministration administration = new RegistrationAdministration(appliedRegistration.applicationId(), RegistrationStatus.APPROVED, LocalDateTime.now());
        administrationRepository.add(administration);
        RegistrationDetails request = new RegistrationDetails(
                requestId, "Lexus",
                new Address("1", "1", "1", "1", "1", "1", Country.MALAYSIA),
                appliedRegistration.getBrn(), Structure.SOLE,
                "luqman", "hakim",
                "hakimluqq25@gmail.com", "123"
        );
        InvalidCompanyRegistration exception = Assert.assertThrows(InvalidCompanyRegistration.class, () -> registrator.apply(request));
        System.out.println(exception.getMessage());
    }

    @Test
    public void emailApproved_shouldThrowException(){
        RegistrationAdministration administration = new RegistrationAdministration(appliedRegistration.applicationId(), RegistrationStatus.APPROVED, LocalDateTime.now());
        administrationRepository.add(administration);
        RegistrationDetails request = new RegistrationDetails(
                requestId, "Lexus",
                new Address("1", "1", "1", "1", "1", "1", Country.MALAYSIA),
                "2222", Structure.SOLE,
                "luqman", "hakim",
                appliedRegistration.getEmail(), "123"
        );
        InvalidCompanyRegistration exception = Assert.assertThrows(InvalidCompanyRegistration.class, ()-> registrator.apply(request));
        System.out.println(exception.getMessage());
    }

    @Test
    public void requestApproved_shouldThrowException(){
        RegistrationAdministration administration = new RegistrationAdministration(appliedRegistration.applicationId(), RegistrationStatus.APPROVED, LocalDateTime.now());
        administrationRepository.add(administration);

        RegistrationDetails request = new RegistrationDetails(
                appliedRegistration.getRequestId(), "terraForm",
                new Address("1", "1", "1", "1", "1", "1", Country.MALAYSIA),
                "22222", Structure.SOLE,
                "luqman", "hakim",
                "hakimluqq25@gmail.com", "123"
        );
        InvalidCompanyRegistration exception = Assert.assertThrows(InvalidCompanyRegistration.class, ()-> registrator.apply(request));
        System.out.println(exception.getMessage());
    }

    @Test
    public void registrationNotYetAdministered_shouldThrowException(){
        RegistrationDetails request = new RegistrationDetails(
                appliedRegistration.getRequestId(),
                "terra",
                new Address("1", "1", "1", "1", "1", "1", Country.MALAYSIA),
                "22222", Structure.SOLE,
                "luqman", "hakim",
                "hakimluqq25@gmail.com", "123"
        );
        InvalidCompanyRegistration exception = Assert.assertThrows(InvalidCompanyRegistration.class, ()-> registrator.apply(request));
        System.out.println(exception.getMessage());
    }

    @Test
    public void newRegistration_shouldNotThrowException(){
        RegistrationRequest registrationRequest = new RegistrationRequest(UUID.randomUUID());
        requestRepository.add(registrationRequest);
        RegistrationDetails request = new RegistrationDetails(
                registrationRequest.getId(),
                "terra",
                new Address("1", "1", "1", "1", "1", "1", Country.MALAYSIA),
                "22222", Structure.SOLE,
                "luqman", "hakim",
                "hakimluqq25@gmail.com", "123"
        );
        registrator.apply(request);
    }
}
