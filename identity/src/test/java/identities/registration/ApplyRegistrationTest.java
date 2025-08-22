package identities.registration;

import identities.address.Address;
import identities.address.Country;
import identities.company.Structure;
import identities.employee.Employee;
import identities.employee.EmployeeRole;
import identities.employee.PersonnelDetails;
import identities.employee.spi.EmployeeRepository;
import identities.employee.spi.InMemoryEmployeeRepository;
import identities.registration.api.RegistrationService;
import identities.registration.events.RegistrationSubmitted;
import identities.registration.exception.InvalidRegistrationApplication;
import identities.registration.spi.*;
import identities.registration.usecase.CompanyRegistrationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import port.IntegrationEventPublisher;

import java.time.LocalDateTime;
import java.util.UUID;

public class ApplyRegistrationTest {
    private RegistrationService registrationService;
    private RegistrationRepository registrationRepository;
    RegistrationRequestRepository registrationRequestRepository;
    private EmployeeRepository employeeRepository;
    private RegistrationApplication initialRegistration;
    private UUID requestId;
    private UUID administratorId;

    @Before
    public void setUp() throws Exception {
        registrationRepository = new InMemoryRegistrationRepository();
        registrationRequestRepository = new InMemoryRegistrationRequestRepository();
        employeeRepository = new InMemoryEmployeeRepository();

        IntegrationEventPublisher<RegistrationSubmitted> integrationEventPublisher = new MockRegistrationSubmittedPublisher();
        registrationService = new CompanyRegistrationService(registrationRepository, registrationRequestRepository, employeeRepository,integrationEventPublisher);

        requestId = UUID.randomUUID();
        administratorId = UUID.randomUUID();
        initialRegistration = new RegistrationApplication(requestId,
                new CompanyDetails("Terra", new Address("1", "1", "1",
                        "1", "43900", "sel", Country.MALAYSIA), "2222", Structure.SOLE
                ),
                new AccountAdministratorDetails("1", "1", "username", "123"),
                RegistrationStatus.UNDER_REVIEW, LocalDateTime.now(), 1, null);
    }

    @Test
    public void applyTakenCompanyName_shouldThrowException(){
        RegistrationApplication approvedRegistration = initialRegistration.updateStatus(administratorId, RegistrationStatus.APPROVED);
        registrationRepository.add(approvedRegistration);

        ApplyRegistrationDetails request = new ApplyRegistrationDetails(
                requestId, "Terra",
                new Address("1", "1", "1", "1", "1", "1", Country.MALAYSIA),
                "22222", Structure.SOLE,
                "luqman", "hakim",
                "hakimluqq25@gmail.com", "123"
        );
        InvalidRegistrationApplication exception = Assert.assertThrows(InvalidRegistrationApplication.class, ()-> registrationService.apply(request));
        System.out.println(exception.getMessage());
    }

    @Test
    public void applyTakenBrn_shouldThrowException(){
        RegistrationApplication approvedRegistration = initialRegistration.updateStatus(administratorId, RegistrationStatus.APPROVED);
        registrationRepository.add(approvedRegistration);

        ApplyRegistrationDetails request = new ApplyRegistrationDetails(
                requestId, "Lexus",
                new Address("1", "1", "1", "1", "1", "1", Country.MALAYSIA),
                approvedRegistration.getBrn(), Structure.SOLE,
                "luqman", "hakim",
                "hakimluqq25@gmail.com", "123"
        );

        InvalidRegistrationApplication exception = Assert.assertThrows(InvalidRegistrationApplication.class, ()-> registrationService.apply(request));
        System.out.println(exception.getMessage());
    }

    @Test
    public void usernameIsTaken_shouldThrowException(){
        Employee employee = new Employee(
                UUID.randomUUID(),
                "email@taken.com",
                "123",
                EmployeeRole.PROCUREMENT_MANAGER,
                new PersonnelDetails("luqman", "hakim"));
        employeeRepository.add(employee);

        ApplyRegistrationDetails request = new ApplyRegistrationDetails(
                requestId,
                "Lexus",
                new Address("1", "1", "1", "1", "1", "1", Country.MALAYSIA),
                "2222",
                Structure.SOLE,
                "luqman",
                "hakim",
                employee.getEmail(),
                "123"
        );
        InvalidRegistrationApplication exception = Assert.assertThrows(InvalidRegistrationApplication.class, ()-> registrationService.apply(request));
        System.out.println(exception.getMessage());
    }

    @Test
    public void approveRequestId_shouldThrowException(){
        RegistrationApplication approvedRegistration = initialRegistration.updateStatus(administratorId, RegistrationStatus.APPROVED);
        registrationRepository.add(approvedRegistration);

        ApplyRegistrationDetails request = new ApplyRegistrationDetails(
                approvedRegistration.requestId(),
                "terraForm",
                new Address("1", "1", "1", "1", "1", "1", Country.MALAYSIA),
                "22222",
                Structure.SOLE,
                "luqman",
                "hakim",
                "hakimluqq25@gmail.com",
                "123"
        );
        InvalidRegistrationApplication exception = Assert.assertThrows(InvalidRegistrationApplication.class, ()-> registrationService.apply(request));
        System.out.println(exception.getMessage());
    }

    @Test
    public void underReviewRegistration_shouldThrowException(){
        registrationRepository.add(initialRegistration);
        ApplyRegistrationDetails request = new ApplyRegistrationDetails(
                initialRegistration.requestId(),
                "terra",
                new Address("1", "1", "1", "1", "1", "1", Country.MALAYSIA),
                "22222",
                Structure.SOLE,
                "luqman",
                "hakim",
                "hakimluqq25@gmail.com",
                "123"
        );
        InvalidRegistrationApplication exception = Assert.assertThrows(InvalidRegistrationApplication.class, ()-> registrationService.apply(request));
        System.out.println(exception.getMessage());
    }

    @Test
    public void newRegistration_shouldNotThrowException(){
        RegistrationRequest registrationRequest = new RegistrationRequest(UUID.randomUUID());
        registrationRequestRepository.add(registrationRequest);
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

    @Test
    public void applyNewRegistration_versionShouldBeOne(){
        RegistrationRequest registrationRequest = new RegistrationRequest(UUID.randomUUID());
        registrationRequestRepository.add(registrationRequest);
        ApplyRegistrationDetails request = new ApplyRegistrationDetails(
                registrationRequest.getId(),
                "terra",
                new Address("1", "1", "1", "1", "1", "1", Country.MALAYSIA),
                "22222", Structure.SOLE,
                "luqman", "hakim",
                "hakimluqq25@gmail.com", "123"
        );
        registrationService.apply(request);
        RegistrationApplication submittedRegistration = registrationRepository.getLatest(registrationRequest.getId());
        Assert.assertNotNull(submittedRegistration);
        Assert.assertEquals(1, submittedRegistration.version());
    }
}
