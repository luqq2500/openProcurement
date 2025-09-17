package usecase.registration;

import domain.account.Account;
import domain.registration.RegistrationRequest;
import usecase.account.spi.AccountRepository;
import usecase.registration.api.RegistrationRequester;
import usecase.registration.spi.RegistrationRequestRepository;

import java.util.UUID;

public class RequestRegistration implements RegistrationRequester {
    private final AccountRepository accountRepository;
    private final RegistrationRequestRepository requestRepository;
    public RequestRegistration(AccountRepository accountRepository, RegistrationRequestRepository requestRepository) {
        this.accountRepository = accountRepository;
        this.requestRepository = requestRepository;
    }
    @Override
    public void request(UUID accountId) {
        Account account = accountRepository.get(accountId);
        RegistrationRequest request = account.requestRegistration();
        requestRepository.add(request);
    }
}
