package usecase.account.spi;

import domain.account.Account;

import java.util.UUID;

public interface AccountRepository {
    void add(Account account);
    Account get(UUID accountId);
}
