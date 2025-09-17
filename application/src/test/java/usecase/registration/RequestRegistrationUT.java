package usecase.registration;

import domain.account.Account;
import domain.account.AccountType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import usecase.account.spi.AccountRepository;
import usecase.account.spi.mock.InMemoryAccountRepository;
import usecase.registration.api.RegistrationRequester;
import usecase.registration.exception.InvalidRegistrationRequest;
import usecase.registration.spi.RegistrationRequestRepository;
import usecase.registration.spi.mock.InMemoryRegistrationRequestRepository;

import java.util.UUID;

public class RequestRegistrationUT {
    private AccountRepository accountRepository;
    private RegistrationRequester registrationRequester;
    @Before
    public void setUp() {
        accountRepository = new InMemoryAccountRepository();
        RegistrationRequestRepository requestRepository = new InMemoryRegistrationRequestRepository();
        registrationRequester = new RequestRegistration(accountRepository, requestRepository);
    }

    @Test
    public void nonGuessAccount_requestRegistration_shouldThrowException() {
        Account basicAccount = new Account(UUID.randomUUID(), AccountType.BASIC);
        Account administratorAccount = new Account(UUID.randomUUID(), AccountType.ADMINISTRATOR);
        accountRepository.add(basicAccount);
        accountRepository.add(administratorAccount);

        Assert.assertThrows(InvalidRegistrationRequest.class, ()-> registrationRequester.request(basicAccount.getAccountId()));
        Assert.assertThrows(InvalidRegistrationRequest.class, ()-> registrationRequester.request(administratorAccount.getAccountId()));

    }

    @Test
    public void guessAccount_requestRegistration_shouldSucceed() {
        Account guessAccount = new Account(UUID.randomUUID(), AccountType.GUESS);
        accountRepository.add(guessAccount);

        registrationRequester.request(guessAccount.getAccountId());
    }
}
