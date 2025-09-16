package usecase.invitation;

import domain.account.Account;
import domain.invitation.UserInvitation;
import event.DomainEventHandler;
import usecase.invitation.api.UserInviter;
import usecase.invitation.events.UserInvited;
import usecase.invitation.exception.InvalidUserInvitation;
import usecase.invitation.spi.UserInvitationRepository;
import usecase.account.spi.AccountRepository;

import java.util.UUID;

public class InviteAUser implements UserInviter {
    private final AccountRepository accountRepository;
    private final UserInvitationRepository userInvitationRepository;

    public InviteAUser(AccountRepository accountRepository, UserInvitationRepository userInvitationRepository) {
        this.accountRepository = accountRepository;
        this.userInvitationRepository = userInvitationRepository;
    }

    @Override
    public void invite(UUID accountId, String email) {
        Account account = accountRepository.get(accountId);
        try{
            UserInvitation userInvitation = account.inviteAUser(email);
            userInvitationRepository.add(userInvitation);
        }catch (InvalidUserInvitation e){
            throw e;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
