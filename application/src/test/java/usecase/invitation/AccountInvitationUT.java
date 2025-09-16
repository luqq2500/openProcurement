package usecase.invitation;

import domain.Entity;
import domain.account.Account;
import domain.account.AccountType;
import event.DomainEventConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import usecase.account.spi.AccountRepository;
import usecase.account.spi.mock.InMemoryAccountRepository;
import usecase.invitation.api.UserInviter;
import usecase.invitation.exception.InvalidUserInvitation;
import usecase.invitation.spi.UserInvitationRepository;
import usecase.invitation.spi.mock.InMemoryUserInvitationRepository;

import java.util.UUID;

public class AccountInvitationUT {
    private UserInviter userInviter;
    private AccountRepository accountRepository;
    @Before
    public void setUp() throws Exception {
        UserInvitationRepository userInvitationRepository = new InMemoryUserInvitationRepository();
        accountRepository = new InMemoryAccountRepository();
        userInviter = new InviteAUser(accountRepository, userInvitationRepository);
        DomainEventConfiguration.registerAllDomainEvents();
    }

    @Test
    public void validAccountType_shouldNotThrowException(){
        Account account = new Account(UUID.randomUUID(), AccountType.ADMINISTRATOR);
        accountRepository.add(account);
        userInviter.invite(account.getAccountId(), "email test");
    }

    @Test
    public void invalidAccountType_shouldThrowException(){
        Account account = new Account(UUID.randomUUID(), AccountType.BASIC);
        accountRepository.add(account);
        Assert.assertThrows(InvalidUserInvitation.class, () -> userInviter.invite(account.getAccountId(), "email test"));
    }
}
