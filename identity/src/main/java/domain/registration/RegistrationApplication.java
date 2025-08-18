package domain.registration;


import java.util.UUID;

public class RegistrationApplication {
    private final UUID id;
    private final UUID requestId;
    private CompanyDetails companyDetails;
    private AccountAdministratorDetails accountAdministratorDetails;
    private RegistrationApplicationStatus status;
    private Integer version;
    public RegistrationApplication(UUID requestId, CompanyDetails companyDetails, AccountAdministratorDetails accountAdministratorDetails) {
        this.id = UUID.randomUUID();
        this.requestId = requestId;
        this.companyDetails = companyDetails;
        this.accountAdministratorDetails = accountAdministratorDetails;
        this.status = RegistrationApplicationStatus.PENDING;
        this.version = 1;
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
    public RegistrationApplication updateAll(){
        updateStatus(RegistrationApplicationStatus.PENDING);
        this.version++;
        return this;
    }
    public boolean isRejected(){
        return this.status == RegistrationApplicationStatus.REJECTED;
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
    public Integer getVersion() {return version;}
}
