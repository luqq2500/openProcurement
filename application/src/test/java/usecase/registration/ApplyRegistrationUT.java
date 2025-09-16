package usecase.registration;

import domain.account.Account;
import domain.account.AccountType;
import domain.registration.*;
import event.DomainEventConfiguration;
import usecase.account.spi.AccountRepository;
import usecase.account.spi.mock.InMemoryAccountRepository;
import usecase.registration.exception.InvalidRegistrationRequest;
import usecase.registration.spi.RegistrationAdministrationRepository;
import usecase.registration.spi.RegistrationRepository;
import usecase.registration.spi.RegistrationRequestRepository;
import usecase.registration.spi.mock.InMemoryRegistrationAdministrationRepository;
import usecase.registration.spi.mock.InMemoryRegistrationRepository;
import usecase.registration.spi.mock.InMemoryRegistrationRequestRepository;
import usecase.registration.api.Registrator;
import usecase.registration.exception.InvalidCompanyRegistration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.UUID;

public class ApplyRegistrationUT {
    private AccountRepository accountRepository;
    private Registrator registrator;
    private RegistrationRequestRepository requestRepository;
    private RegistrationAdministrationRepository administrationRepository;
    private Registration appliedRegistration;
    private UUID requestId;

    @Before
    public void setUp() throws Exception {
        accountRepository = new InMemoryAccountRepository();
        RegistrationRepository registrationRepository = new InMemoryRegistrationRepository();
        requestRepository = new InMemoryRegistrationRequestRepository();
        administrationRepository = new InMemoryRegistrationAdministrationRepository();
        registrator = new ApplyRegistration(accountRepository, registrationRepository, requestRepository, administrationRepository);
        DomainEventConfiguration.registerAllDomainEvents();

        UUID guessId = UUID.randomUUID();
        requestId = UUID.randomUUID();
        UUID applicationId = UUID.randomUUID();
        RegistrationRequest registrationRequested = new RegistrationRequest(guessId);
        requestRepository.add(registrationRequested);
        appliedRegistration = new Registration(registrationRequested.getId(), applicationId,
                new CompanyDetails("1", "222", Structure.SOLE),
                new AccountAdminDetails("luqman", "hakim", "email", "***"),
                LocalDateTime.now());
        registrationRepository.add(appliedRegistration);
    }

    @Test
    public void invalidAccountTypeRequestRegistration_shouldThrowException() {
        Account administratorAccount = new Account(UUID.randomUUID(), AccountType.ADMINISTRATOR);
        Account basicAccount = new Account(UUID.randomUUID(), AccountType.BASIC);
        accountRepository.add(administratorAccount);
        accountRepository.add(basicAccount);

        Assert.assertThrows(InvalidRegistrationRequest.class, ()-> registrator.request(administratorAccount.getAccountId()));
        Assert.assertThrows(InvalidRegistrationRequest.class, ()-> registrator.request(basicAccount.getAccountId()));
    }

    @Test
    public void validAccountTypeRequestRegistration_shouldBeSuccessful() {
        Account account = new Account(UUID.randomUUID(), AccountType.GUESS);
        accountRepository.add(account);
        registrator.request(account.getAccountId());
    }

    @Test
    public void companyNameApproved_shouldThrowException(){
        RegistrationAdministration administration = new RegistrationAdministration(appliedRegistration.applicationId(), RegistrationStatus.APPROVED, LocalDateTime.now());
        administrationRepository.add(administration);
        RegistrationDetails request = new RegistrationDetails(
                requestId, appliedRegistration.getCompanyName(),
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
                "22222", Structure.SOLE,
                "luqman", "hakim",
                "hakimluqq25@gmail.com", "123"
        );
        registrator.apply(request);
    }
}
