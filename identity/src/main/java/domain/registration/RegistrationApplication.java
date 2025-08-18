package domain.registration;


import java.util.UUID;

public class RegistrationApplication {
    private final UUID id;
    private final UUID requestId;
    private CompanyDetails companyDetails;
    private AccountAdministratorDetails accountAdministratorDetails;
    private RegistrationApplicationStatus status;
    private Integer updateVersion;
    public RegistrationApplication(UUID requestId, CompanyDetails companyDetails, AccountAdministratorDetails accountAdministratorDetails) {
        this.id = UUID.randomUUID();
        this.requestId = requestId;
        this.companyDetails = companyDetails;
        this.accountAdministratorDetails = accountAdministratorDetails;
        this.status = RegistrationApplicationStatus.IN_PROGRESS;
        this.updateVersion = 1;
    }
    public void updateStatus(RegistrationApplicationStatus newStatus) {
        this.status.checkStatusChangeTo(newStatus);
        this.status = newStatus;
    }
    public RegistrationApplication updateCompanyDetails(CompanyDetails companyDetails) {
        this.companyDetails = companyDetails;
        return this;
    }
    public RegistrationApplication updateAccountAdministratorDetails(AccountAdministratorDetails accountAdministratorDetails) {
        this.accountAdministratorDetails = accountAdministratorDetails;
        return this;
    }
    public RegistrationApplication confirmUpdate(){
        this.updateVersion++;
        return this;
    }
    public UUID getId() {return id;}
    public UUID getRequestId() {return requestId;}
    public CompanyDetails getCompanyDetails() {return companyDetails;}
    public AccountAdministratorDetails getAccountAdministratorDetails() {return accountAdministratorDetails;}
    public Integer getUpdateVersion() {return updateVersion;}
}
