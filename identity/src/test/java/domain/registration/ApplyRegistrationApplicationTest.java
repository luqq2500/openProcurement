package domain.registration;

import domain.address.Address;
import domain.address.Country;
import domain.company.Structure;
import domain.employee.Employee;
import domain.employee.EmployeeRole;
import domain.employee.PersonnelDetails;
import domain.employee.spi.EmployeeRepository;
import domain.registration.api.RegistrationApplier;
import domain.registration.events.RegistrationSubmitted;
import domain.registration.exception.InvalidRegistrationApplication;
import domain.registration.spi.RegistrationRepository;
import domain.registration.spi.RegistrationRequestRepository;
import domain.registration.usecases.ApplyRegistration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import port.IntegrationEventPublisher;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class ApplyRegistrationApplicationTest {
    private RegistrationApplier applier;
    private RegistrationRepository registrationRepository;
    private RegistrationRequestRepository registrationRequestRepository;
    private EmployeeRepository employeeRepository;
    private IntegrationEventPublisher<RegistrationSubmitted> integrationEventPublisher;
    private RegistrationApplication initialRegistrationApplication;
    private UUID registrationId;
    private UUID guessAccountId;

    @Before
    public void setUp() throws Exception {
        registrationRepository = new InMemoryRegistrationRepository();
        registrationRequestRepository = new InMemoryRegistrationRequestRepository();
        employeeRepository = new InMemoryEmployeeRepository();
        integrationEventPublisher = new MockRegistrationSubmittedPublisher();
        applier = new ApplyRegistration(registrationRepository, registrationRequestRepository, employeeRepository, integrationEventPublisher);

        registrationId = UUID.randomUUID();
        guessAccountId = UUID.randomUUID();
        initialRegistrationApplication = new RegistrationApplication(registrationId, guessAccountId,
                new CompanyDetails("Terra", new Address("1", "1", "1",
                        "1", "43900", "sel", Country.MALAYSIA), "2222", Structure.SOLE
                ),
                new AccountAdministratorDetails("1", "1", "username", "123"),
                RegistrationApplicationStatus.UNDER_REVIEW, LocalDateTime.now(), 1);
    }

    @Test
    public void companyNameIsTaken_shouldThrowException(){
        RegistrationApplication approvedRegistration = initialRegistrationApplication.updateStatus(RegistrationApplicationStatus.APPROVED);
        registrationRepository.add(approvedRegistration);

        RegistrationApplierRequest request = new RegistrationApplierRequest(
                registrationId,
                "terra",
                new Address("1", "1", "1", "1", "1", "1", Country.MALAYSIA),
                "22222",
                Structure.SOLE,
                "luqman",
                "hakim",
                "hakimluqq25@gmail.com",
                "123"
        );
        InvalidRegistrationApplication exception = Assert.assertThrows(InvalidRegistrationApplication.class, ()-> applier.apply(request));
        System.out.println(exception.getMessage());
    }

    @Test
    public void companyBrnIsTaken_shouldThrowException(){
        UUID requestId = UUID.randomUUID();
        RegistrationApplication registration = new RegistrationApplication(
                requestId,
                new CompanyDetails(
                        "terra",
                        new Address("1", "1", "1", "1", "1", "1", Country.MALAYSIA),
                        "2222",
                        Structure.SOLE
                ),
                new AccountAdministratorDetails("d", "d", "d", "d")
        );
        registration.updateStatus(RegistrationApplicationStatus.APPROVED);
        registrationRepository.add(registration);

        RegistrationApplierRequest request = new RegistrationApplierRequest(
                requestId,
                "Lexus",
                new Address("1", "1", "1", "1", "1", "1", Country.MALAYSIA),
                "2222",
                Structure.SOLE,
                "luqman",
                "hakim",
                "hakimluqq25@gmail.com",
                "123"
        );
        InvalidRegistrationApplication exception = Assert.assertThrows(InvalidRegistrationApplication.class, ()-> applier.apply(request));
        System.out.println(exception.getMessage());
    }

    @Test
    public void usernameIsTaken_shouldThrowException(){
        UUID requestId = UUID.randomUUID();
        Employee employee = new Employee(
                UUID.randomUUID(),
                "email@taken.com",
                "123",
                EmployeeRole.PROCUREMENT_MANAGER,
                new PersonnelDetails("luqman", "hakim"));
        employeeRepository.add(employee);

        RegistrationApplierRequest request = new RegistrationApplierRequest(
                requestId,
                "Lexus",
                new Address("1", "1", "1", "1", "1", "1", Country.MALAYSIA),
                "2222",
                Structure.SOLE,
                "luqman",
                "hakim",
                "email@taken.com",
                "123"
        );
        InvalidRegistrationApplication exception = Assert.assertThrows(InvalidRegistrationApplication.class, ()-> applier.apply(request));
        System.out.println(exception.getMessage());
    }

    @Test
    public void approveRegistration_shouldThrowException(){
        UUID requestId = UUID.randomUUID();
        RegistrationApplication registration = new RegistrationApplication(
                requestId,
                new CompanyDetails(
                        "terra",
                        new Address("1", "1", "1", "1", "1", "1", Country.MALAYSIA),
                        "2222",
                        Structure.SOLE
                ),
                new AccountAdministratorDetails("d", "d", "d", "d")
        );
        registration.updateStatus(RegistrationApplicationStatus.APPROVED);
        registrationRepository.add(registration);

        RegistrationApplierRequest request = new RegistrationApplierRequest(
                requestId,
                "terraForm",
                new Address("1", "1", "1", "1", "1", "1", Country.MALAYSIA),
                "22222",
                Structure.SOLE,
                "luqman",
                "hakim",
                "hakimluqq25@gmail.com",
                "123"
        );
        InvalidRegistrationApplication exception = Assert.assertThrows(InvalidRegistrationApplication.class, ()-> applier.apply(request));
        System.out.println(exception.getMessage());
    }

    @Test
    public void underReviewRegistration_shouldThrowException(){
        UUID requestId = UUID.randomUUID();
        RegistrationApplication registration = new RegistrationApplication(
                requestId,
                new CompanyDetails(
                        "terra",
                        new Address("1", "1", "1", "1", "1", "1", Country.MALAYSIA),
                        "2222",
                        Structure.SOLE
                ),
                new AccountAdministratorDetails("d", "d", "d", "d")
        );
        registrationRepository.add(registration);

        RegistrationApplierRequest request = new RegistrationApplierRequest(
                requestId,
                "terra",
                new Address("1", "1", "1", "1", "1", "1", Country.MALAYSIA),
                "22222",
                Structure.SOLE,
                "luqman",
                "hakim",
                "hakimluqq25@gmail.com",
                "123"
        );
        InvalidRegistrationApplication exception = Assert.assertThrows(InvalidRegistrationApplication.class, ()-> applier.apply(request));
        System.out.println(exception.getMessage());
    }

    @Test
    public void updateRejectedApplication_versionShouldIncremented(){
        UUID requestId = UUID.randomUUID();
        RegistrationApplication rejectedRegistration = new RegistrationApplication(
                requestId, new CompanyDetails("terra",
                        new Address("1", "1", "1", "1", "1", "1", Country.MALAYSIA),
                        "2222", Structure.SOLE
                ), new AccountAdministratorDetails("d", "d", "d", "d")
        );
        rejectedRegistration.updateStatus(RegistrationApplicationStatus.REJECTED);
        registrationRepository.add(rejectedRegistration);

        RegistrationApplierRequest request = new RegistrationApplierRequest(
                requestId, "Terra Sdn Bhd",
                new Address("1", "1", "1", "1", "1", "1", Country.MALAYSIA),
                "22222", Structure.SOLE,
                "luqman", "hakim", "hakimluqq25@gmail.com", "123"
        );
        applier.apply(request);
        Optional<RegistrationApplication> submittedRegistration = registrationRepository.findLatestByRequestId(requestId);
        System.out.println(rejectedRegistration.getVersion());
        System.out.println(submittedRegistration.get().getVersion());
    }
}
