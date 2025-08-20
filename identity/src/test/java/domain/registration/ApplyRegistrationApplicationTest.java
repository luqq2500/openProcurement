package domain.registration;

import domain.address.Address;
import domain.address.Country;
import domain.administrator.Administrator;
import domain.administrator.AdministratorRole;
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
import domain.registration.usecase.ApplyRegistration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import port.IntegrationEventPublisher;

import java.time.LocalDateTime;
import java.util.UUID;

public class ApplyRegistrationApplicationTest {
    private RegistrationApplier applier;
    private RegistrationRepository registrationRepository;
    RegistrationRequestRepository registrationRequestRepository;
    private EmployeeRepository employeeRepository;
    private RegistrationApplication initialUnderReviewRegistration;
    private UUID registrationId;
    private UUID requestId;
    private UUID administratorId;
    private Administrator mockIdentityAdministrator;

    @Before
    public void setUp() throws Exception {
        registrationRepository = new InMemoryRegistrationRepository();
        registrationRequestRepository = new InMemoryRegistrationRequestRepository();
        employeeRepository = new InMemoryEmployeeRepository();
        IntegrationEventPublisher<RegistrationSubmitted> integrationEventPublisher = new MockRegistrationSubmittedPublisher();
        applier = new ApplyRegistration(registrationRepository, registrationRequestRepository, employeeRepository, integrationEventPublisher);

        registrationId = UUID.randomUUID();
        requestId = UUID.randomUUID();
        mockIdentityAdministrator = new Administrator(new PersonnelDetails("1", "1"), AdministratorRole.IDENTITY_ADMINISTRATOR);
        initialUnderReviewRegistration = new RegistrationApplication(registrationId,
                new CompanyDetails("Terra", new Address("1", "1", "1",
                        "1", "43900", "sel", Country.MALAYSIA), "2222", Structure.SOLE
                ),
                new AccountAdministratorDetails("1", "1", "username", "123"),
                RegistrationApplicationStatus.UNDER_REVIEW, LocalDateTime.now(), requestId, null);
    }

    @Test
    public void companyNameIsTaken_shouldThrowException(){
        RegistrationApplication approvedRegistration = initialUnderReviewRegistration.updateStatus(mockIdentityAdministrator, RegistrationApplicationStatus.APPROVED);
        registrationRepository.add(approvedRegistration);

        RegistrationApplierRequest request = new RegistrationApplierRequest(
                requestId,
                "Terra",
                new Address("1", "1", "1", "1", "1", "1", Country.MALAYSIA),
                "22222", Structure.SOLE,
                "luqman", "hakim",
                "hakimluqq25@gmail.com", "123"
        );
        InvalidRegistrationApplication exception = Assert.assertThrows(InvalidRegistrationApplication.class, ()-> applier.apply(request));
        System.out.println(exception.getMessage());
    }

    @Test
    public void companyBrnIsTaken_shouldThrowException(){
        RegistrationApplication registration = new RegistrationApplication(
                registrationId,
                new CompanyDetails(
                        "terra",
                        new Address("1", "1", "1", "1", "1", "1", Country.MALAYSIA),
                        "2222", Structure.SOLE),
                new AccountAdministratorDetails("d", "d", "d", "d"),
                RegistrationApplicationStatus.APPROVED, LocalDateTime.now(),
                requestId, mockIdentityAdministrator.getAdministratorId()
        );
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
    public void approveRequestId_shouldThrowException(){
        RegistrationApplication approvedRegistration = initialUnderReviewRegistration.updateStatus(mockIdentityAdministrator, RegistrationApplicationStatus.APPROVED);
        registrationRepository.add(approvedRegistration);

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
        registrationRepository.add(initialUnderReviewRegistration);
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
    public void changeDetailsFromPreviouslyRejected_registrationIdShouldDifferAndRequestIdIsSame(){
        RegistrationApplication rejectedApplication = initialUnderReviewRegistration.updateStatus(mockIdentityAdministrator, RegistrationApplicationStatus.REJECTED);
        registrationRepository.add(rejectedApplication);
        RegistrationApplierRequest request = new RegistrationApplierRequest(
                requestId,
                "terra",
                new Address("1", "1", "1", "1", "1", "1", Country.MALAYSIA),
                "22222", Structure.SOLE,
                "luqman", "hakim",
                "hakimluqq25@gmail.com", "123"
        );
        applier.apply(request);

        Assert.assertNotEquals(
                registrationRepository.findLatestByRequestId(requestId).get().registrationId(),
                registrationRepository.get(rejectedApplication.registrationId()).registrationId());
        Assert.assertEquals(
                registrationRepository.findLatestByRequestId(requestId).get().requestId(),
                registrationRepository.get(rejectedApplication.registrationId()).requestId()
        );
    }

    @Test
    public void newRegistration_shouldNotThrowException(){
        RegistrationRequest registrationRequest = new RegistrationRequest(UUID.randomUUID());
        registrationRequestRepository.add(registrationRequest);
        RegistrationApplierRequest request = new RegistrationApplierRequest(
                registrationRequest.getId(),
                "terra",
                new Address("1", "1", "1", "1", "1", "1", Country.MALAYSIA),
                "22222", Structure.SOLE,
                "luqman", "hakim",
                "hakimluqq25@gmail.com", "123"
        );
        applier.apply(request);
    }


}
