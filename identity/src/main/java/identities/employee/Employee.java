package identities.employee;

import identities.account.AccountStatus;

import java.util.UUID;

public class Employee {
    private final UUID id;
    private final UUID companyAccountId;
    private String email;
    private String password;
    private EmployeeRole role;
    private PersonnelDetails personnelDetails;
    private AccountStatus accountStatus;
    private boolean isAdministratorAccount;
    public Employee(UUID companyAccountId, String email, String password, EmployeeRole role, PersonnelDetails personnelDetails) {
        this.id = UUID.randomUUID();
        this.companyAccountId = companyAccountId;
        this.email = email;
        this.password = password;
        this.role = role;
        this.personnelDetails = personnelDetails;
        this.accountStatus = AccountStatus.ACTIVE;
        this.isAdministratorAccount = false;
    }
    public boolean isActive(){return this.accountStatus.equals(AccountStatus.ACTIVE);}
    public String getEmail(){return this.email;}
    public void changeEmail(String newEmail) {this.email = newEmail;}
    public void elevateAccountToAdministrator() {
        isAdministratorAccount = true;
    }
}
