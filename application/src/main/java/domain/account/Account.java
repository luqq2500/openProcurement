package domain.account;

import domain.Entity;
import domain.invitation.UserInvitation;
import domain.registration.AccountAdminDetails;
import domain.registration.CompanyDetails;
import domain.registration.Registration;
import domain.registration.RegistrationRequest;
import usecase.invitation.events.UserInvited;
import usecase.invitation.exception.InvalidUserInvitation;
import usecase.registration.events.RegistrationRequested;
import usecase.registration.exception.InvalidRegistrationRequest;

import java.time.LocalDateTime;
import java.util.UUID;

public class Account extends Entity {
    private final UUID accountId;
    private final AccountType accountType;
    public Account(UUID accountId, AccountType accountType) {
        this.accountId = accountId;
        this.accountType = accountType;
    }
    public UserInvitation inviteAUser(String email){
        if (accountType.equals(AccountType.ADMINISTRATOR)){
            UserInvitation userInvitation = new UserInvitation(UUID.randomUUID(), accountId, email, LocalDateTime.now());
            this.publishEvent(new UserInvited(userInvitation));
            return userInvitation;
        }else {
            throw new InvalidUserInvitation("Only administrators can invite users.");
        }
    }
    public RegistrationRequest requestRegistration(){
        if (accountType.equals(AccountType.GUESS)){
            RegistrationRequest request = new RegistrationRequest(accountId);
            this.publishEvent(new RegistrationRequested(request));
            return request;
        }else {
            throw new InvalidRegistrationRequest("Only guests can invite users.");
        }
    }
    public UUID getAccountId() {
        return accountId;
    }
}
