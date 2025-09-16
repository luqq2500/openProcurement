package usecase.account.spi.mock;

import domain.account.Account;
import usecase.account.spi.AccountRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryAccountRepository implements AccountRepository {
    private final Map<UUID, Account> accounts = new HashMap<>();
    @Override
    public void add(Account account) {
        accounts.put(account.getAccountId(), account);
    }
    @Override
    public Account get(UUID accountId) {
        return accounts.get(accountId);
    }
}
