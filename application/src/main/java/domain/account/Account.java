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
import usecase.registration.events.RegistrationSubmitted;
import usecase.registration.exception.InvalidCompanyRegistration;
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
        if (!accountType.equals(AccountType.ADMINISTRATOR)){
            throw new InvalidUserInvitation("Only administrators can invite users.");
        }
        UserInvitation userInvitation = new UserInvitation(UUID.randomUUID(), accountId, email, LocalDateTime.now());
        this.publishEvent(new UserInvited(userInvitation));
        return userInvitation;
    }
    public RegistrationRequest requestRegistration(){
        if (!accountType.equals(AccountType.GUESS)){
            throw new InvalidRegistrationRequest("Only guests can invite users.");
        }
        RegistrationRequest request = new RegistrationRequest(accountId);
        this.publishEvent(new RegistrationRequested(request));
        return request;
    }
    public Registration register(CompanyDetails companyDetails, AccountAdminDetails accountAdminDetails){
        if (!accountType.equals(AccountType.GUESS)){
            throw new InvalidCompanyRegistration("Only guests can register account.");
        }
        Registration registration = new Registration(UUID.randomUUID(), accountId, companyDetails, accountAdminDetails, LocalDateTime.now(), 1);
        this.publishEvent(new RegistrationSubmitted(registration));
        return registration;
    }
    public Registration resubmitRegistration(Registration oldRegistration, CompanyDetails companyDetails, AccountAdminDetails accountAdminDetails){
        if (!accountType.equals(AccountType.GUESS)){
            throw new InvalidCompanyRegistration("Only guests can resubmit account.");
        }
        if (!oldRegistration.accountId().equals(accountId)){
            throw new InvalidCompanyRegistration("Old account id does not match account id.");
        }
        Registration registration = new Registration(oldRegistration.registrationId(), accountId, companyDetails, accountAdminDetails, LocalDateTime.now(), oldRegistration.version()+1);
        this.publishEvent(new RegistrationSubmitted(registration));
        return registration;
    }
    public UUID getAccountId() {
        return accountId;
    }
}
