package domain.registration;


import domain.registration.exception.InvalidRegistrationApplication;

import java.time.LocalDateTime;
import java.util.UUID;

public class RegistrationApplication {
    private final UUID id;
    private final UUID requestId;
    private final CompanyDetails companyDetails;
    private final AccountAdministratorDetails accountAdministratorDetails;
    private final LocalDateTime appliedOn;
    private RegistrationApplicationStatus status;

    public RegistrationApplication(UUID requestId, CompanyDetails companyDetails, AccountAdministratorDetails accountAdministratorDetails) {
        this.id = UUID.randomUUID();
        this.requestId = requestId;
        this.companyDetails = companyDetails;
        this.accountAdministratorDetails = accountAdministratorDetails;
        this.appliedOn = LocalDateTime.now();
        this.status = RegistrationApplicationStatus.UNDER_REVIEW;
    }
    public RegistrationApplication editDetails(CompanyDetails companyDetails, AccountAdministratorDetails accountAdministratorDetails) {
        if (!status.equals(RegistrationApplicationStatus.REJECTED)) {
            throw new InvalidRegistrationApplication("Registration cannot be updated.");
        }
        return new RegistrationApplication(requestId, companyDetails, accountAdministratorDetails);
    }

    public void updateStatus(RegistrationApplicationStatus newStatus) {
        this.status.checkStatusChangeTo(newStatus);
        this.status = newStatus;
    }

    public boolean isUnderReview() {
        return this.status == RegistrationApplicationStatus.UNDER_REVIEW;
    }
    public boolean isRejected(){
        return this.status == RegistrationApplicationStatus.REJECTED;
    }
    public boolean isApproved() {
        return this.status == RegistrationApplicationStatus.APPROVED;
    }
    public String getCompanyName(){
        return companyDetails.companyName();
    }
    public String getBrn(){
        return companyDetails.brn();
    }
    public String getUsername(){
        return accountAdministratorDetails.username();
    }
    public UUID getId() {return id;}
    public UUID getRequestId() {return requestId;}
    public CompanyDetails getCompanyDetails() {return companyDetails;}
    public AccountAdministratorDetails getAccountAdministratorDetails() {return accountAdministratorDetails;}

    public LocalDateTime getAppliedOn() {
        return appliedOn;
    }
}
