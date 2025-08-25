package identities.registration;

import identities.address.Address;
import identities.address.Country;
import identities.company.Structure;
import identities.employee.Employee;
import identities.employee.EmployeeRole;
import identities.employee.PersonnelDetails;
import identities.employee.spi.EmployeeRepository;
import identities.employee.spi.InMemoryEmployeeRepository;
import identities.registration.events.MockRegistrationSubmittedPublisher;
import identities.registration.spi.*;
import identities.registration.api.RegistrationService;
import identities.registration.events.RegistrationSubmitted;
import usecase.exception.InvalidCompanyRegistration;
import usecase.RegisterACompany;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import port.IntegrationEventPublisher;

import java.util.UUID;

public class ApplyRegistrationTest {
    private RegistrationService registrationService;
    private RegistrationRepository registrationRepository;
    RegistrationRequestRepository registrationRequestRepository;
    private EmployeeRepository employeeRepository;
    private RegistrationApplication registration;
    private UUID requestId;
    private UUID administratorId;

    @Before
    public void setUp() throws Exception {
        registrationRepository = new InMemoryRegistrationRepository();
        registrationRequestRepository = new InMemoryRegistrationRequestRepository();
        employeeRepository = new InMemoryEmployeeRepository();

        IntegrationEventPublisher<RegistrationSubmitted> integrationEventPublisher = new MockRegistrationSubmittedPublisher();
        registrationService = new RegisterACompany(registrationRepository, registrationRequestRepository, employeeRepository,integrationEventPublisher);

        requestId = UUID.randomUUID();
        administratorId = UUID.randomUUID();
        registration = new RegistrationApplication(requestId,
                new CompanyDetails("Terra", new Address("1", "1", "1",
                        "1", "43900", "sel", Country.MALAYSIA), "2222", Structure.SOLE
                ),
                new AccountAdministratorDetails("1", "1", "username", "123"));
    }

    @Test
    public void applyTakenCompanyName_shouldThrowException(){
        registration.administer(administratorId, RegistrationStatus.APPROVED, "");
        registrationRepository.add(registration);

        ApplyRegistrationDetails request = new ApplyRegistrationDetails(
                requestId, "Terra",
                new Address("1", "1", "1", "1", "1", "1", Country.MALAYSIA),
                "22222", Structure.SOLE,
                "luqman", "hakim",
                "hakimluqq25@gmail.com", "123"
        );
        InvalidCompanyRegistration exception = Assert.assertThrows(InvalidCompanyRegistration.class, ()-> registrationService.apply(request));
        System.out.println(exception.getMessage());
    }

    @Test
    public void applyTakenBrn_shouldThrowException(){
        registration.administer(administratorId, RegistrationStatus.APPROVED, "");
        registrationRepository.add(registration);

        ApplyRegistrationDetails request = new ApplyRegistrationDetails(
                requestId, "Lexus",
                new Address("1", "1", "1", "1", "1", "1", Country.MALAYSIA),
                registration.getBrn(), Structure.SOLE,
                "luqman", "hakim",
                "hakimluqq25@gmail.com", "123"
        );

        InvalidCompanyRegistration exception = Assert.assertThrows(InvalidCompanyRegistration.class, ()-> registrationService.apply(request));
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
        InvalidCompanyRegistration exception = Assert.assertThrows(InvalidCompanyRegistration.class, ()-> registrationService.apply(request));
        System.out.println(exception.getMessage());
    }

    @Test
    public void approveRequestId_shouldThrowException(){
        registration.administer(administratorId, RegistrationStatus.APPROVED, "");
        registrationRepository.add(registration);

        ApplyRegistrationDetails request = new ApplyRegistrationDetails(
                registration.getRequestId(),
                "terraForm",
                new Address("1", "1", "1", "1", "1", "1", Country.MALAYSIA),
                "22222",
                Structure.SOLE,
                "luqman",
                "hakim",
                "hakimluqq25@gmail.com",
                "123"
        );
        InvalidCompanyRegistration exception = Assert.assertThrows(InvalidCompanyRegistration.class, ()-> registrationService.apply(request));
        System.out.println(exception.getMessage());
    }

    @Test
    public void underReviewRegistration_shouldThrowException(){
        registrationRepository.add(registration);
        ApplyRegistrationDetails request = new ApplyRegistrationDetails(
                registration.getRequestId(),
                "terra",
                new Address("1", "1", "1", "1", "1", "1", Country.MALAYSIA),
                "22222",
                Structure.SOLE,
                "luqman",
                "hakim",
                "hakimluqq25@gmail.com",
                "123"
        );
        InvalidCompanyRegistration exception = Assert.assertThrows(InvalidCompanyRegistration.class, ()-> registrationService.apply(request));
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
        RegistrationApplication submittedRegistration = registrationRepository.get(registrationRequest.getId());
        Assert.assertNotNull(submittedRegistration);
    }
}
