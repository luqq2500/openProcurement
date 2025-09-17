package usecase.registration;

import domain.account.Account;
import domain.account.AccountType;
import domain.registration.*;
import event.DomainEventConfiguration;
import usecase.account.spi.AccountRepository;
import usecase.account.spi.mock.InMemoryAccountRepository;
import usecase.registration.dto.RegistrationDetails;
import usecase.registration.spi.RegistrationAdministrationRepository;
import usecase.registration.spi.RegistrationRepository;
import usecase.registration.spi.RegistrationRequestRepository;
import usecase.registration.spi.mock.InMemoryRegistrationAdministrationRepository;
import usecase.registration.spi.mock.InMemoryRegistrationRepository;
import usecase.registration.spi.mock.InMemoryRegistrationRequestRepository;
import usecase.registration.api.RegistrationApplier;
import usecase.registration.exception.InvalidCompanyRegistration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.UUID;

public class ApplyRegistrationUT {
    private RegistrationApplier registrationApplier;
    private RegistrationAdministrationRepository administrationRepository;
    private Registration appliedRegistration;
    private Account account;

    @Before
    public void setUp() throws Exception {
        AccountRepository accountRepository = new InMemoryAccountRepository();
        RegistrationRepository registrationRepository = new InMemoryRegistrationRepository();
        administrationRepository = new InMemoryRegistrationAdministrationRepository();
        registrationApplier = new ApplyRegistration(accountRepository, registrationRepository, administrationRepository);
        DomainEventConfiguration.registerAllDomainEvents();

        account = new Account(UUID.randomUUID(), AccountType.GUESS);
        accountRepository.add(account);
        appliedRegistration = new Registration(UUID.randomUUID(), account.getAccountId(),
                new CompanyDetails("1", "222", Structure.SOLE),
                new AccountAdminDetails("luqman", "hakim", "email", "***"),
                LocalDateTime.now(),0);
        registrationRepository.add(appliedRegistration);
    }

    @Test
    public void newRegistration_shouldSucceed(){
        RegistrationDetails request = new RegistrationDetails(
                account.getAccountId(),null,
                "terra",
                "22222", Structure.SOLE,
                "luqman", "hakim",
                "hakimluqq25@gmail.com", "123"
        );
        registrationApplier.apply(request);
    }

    @Test
    public void notExistRegistration_shouldThrowException(){
        RegistrationDetails request = new RegistrationDetails(
                account.getAccountId(),UUID.randomUUID(),
                "terra",
                "22222", Structure.SOLE,
                "luqman", "hakim",
                "hakimluqq25@gmail.com", "123"
        );
        InvalidCompanyRegistration invalidRegistration = Assert.assertThrows(InvalidCompanyRegistration.class, () -> registrationApplier.apply(request));
        System.out.println(invalidRegistration.getMessage());
    }

    @Test
    public void companyNameTaken_shouldThrowException(){
        RegistrationAdministration administration = new RegistrationAdministration(appliedRegistration.registrationId(), RegistrationStatus.APPROVED, LocalDateTime.now(),1);
        administrationRepository.add(administration);
        RegistrationDetails request = new RegistrationDetails(
                appliedRegistration.accountId(), null,
                appliedRegistration.getCompanyName(), "22222", Structure.SOLE,
                "luqman", "hakim", "hakimluqq25@gmail.com", "123"
        );
        InvalidCompanyRegistration exception = Assert.assertThrows(InvalidCompanyRegistration.class, ()-> registrationApplier.apply(request));
        System.out.println(exception.getMessage());
    }

    @Test
    public void brnTaken_shouldThrowException() {
        RegistrationAdministration administration = new RegistrationAdministration(appliedRegistration.registrationId(), RegistrationStatus.APPROVED, LocalDateTime.now(),1);
        administrationRepository.add(administration);
        RegistrationDetails request = new RegistrationDetails(
                appliedRegistration.accountId(), null,
                "Lexus", appliedRegistration.getBrn(), Structure.SOLE,
                "luqman", "hakim", "hakimluqq25@gmail.com", "123"
        );
        InvalidCompanyRegistration exception = Assert.assertThrows(InvalidCompanyRegistration.class, () -> registrationApplier.apply(request));
        System.out.println(exception.getMessage());
    }

    @Test
    public void emailTaken_shouldThrowException(){
        RegistrationAdministration administration = new RegistrationAdministration(appliedRegistration.registrationId(), RegistrationStatus.APPROVED, LocalDateTime.now(),1);
        administrationRepository.add(administration);
        RegistrationDetails request = new RegistrationDetails(
                appliedRegistration.accountId(), null,
                "Lexus", "2222", Structure.SOLE,
                "luqman", "hakim", appliedRegistration.getEmail(), "123"
        );
        InvalidCompanyRegistration exception = Assert.assertThrows(InvalidCompanyRegistration.class, ()-> registrationApplier.apply(request));
        System.out.println(exception.getMessage());
    }

    @Test
    public void applyAfterAdministrationApproved_shouldThrowException(){
        RegistrationAdministration administration = new RegistrationAdministration(appliedRegistration.registrationId(), RegistrationStatus.APPROVED, LocalDateTime.now(),1);
        administrationRepository.add(administration);

        RegistrationDetails registration = new RegistrationDetails(
                appliedRegistration.accountId(), appliedRegistration.registrationId(),
                "new name", "new brn", Structure.SOLE,
                "new first name", "new last name", "new email", "123"
        );
        InvalidCompanyRegistration exception = Assert.assertThrows(InvalidCompanyRegistration.class, ()-> registrationApplier.apply(registration));
        System.out.println(exception.getMessage());
    }

    @Test
    public void applyTwiceWithNoAdministration_shouldThrowException(){
        RegistrationDetails registration = new RegistrationDetails(
                appliedRegistration.accountId(), appliedRegistration.registrationId(),
                appliedRegistration.getCompanyName(), appliedRegistration.getBrn(), Structure.SOLE,
                appliedRegistration.getFirstName(), appliedRegistration.getLastName(), appliedRegistration.getEmail(), "123"
        );
        InvalidCompanyRegistration exception = Assert.assertThrows(InvalidCompanyRegistration.class, ()-> registrationApplier.apply(registration));
        System.out.println(exception.getMessage());
    }

    @Test
    public void applyAfterAdministrationRejected_shouldSucceed(){
        RegistrationAdministration administration = new RegistrationAdministration(appliedRegistration.registrationId(), RegistrationStatus.REJECTED, LocalDateTime.now(),1);
        administrationRepository.add(administration);

        RegistrationDetails registration = new RegistrationDetails(
                appliedRegistration.accountId(), appliedRegistration.registrationId(),
                appliedRegistration.getCompanyName(), appliedRegistration.getBrn(), Structure.SOLE,
                appliedRegistration.getFirstName(), appliedRegistration.getLastName(), appliedRegistration.getEmail(), "123"
        );
        registrationApplier.apply(registration);
    }
}
