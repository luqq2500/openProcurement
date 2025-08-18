package domain.account.employee;

import domain.account.AccountStatus;

import java.util.UUID;

public class EmployeeAccount {
    private final UUID id;
    private final UUID companyAccountId;
    private PersonnelDetails personnelDetails;
    private EmployeeRole role;
    private String password;
    private AccountStatus accountStatus;
    private boolean isAdministratorAccount;
    public EmployeeAccount(UUID companyAccountId, PersonnelDetails personnelDetails, String password, EmployeeRole role) {
        this.id = UUID.randomUUID();
        this.companyAccountId = companyAccountId;
        this.personnelDetails = personnelDetails;
        this.password = password;
        this.role = role;
        this.accountStatus = AccountStatus.ACTIVE;
        this.isAdministratorAccount = false;
    }
    public void changeEmail(String newEmail) {
        personnelDetails.changeEmail(newEmail);
    }
    public void elevateAccountToAdministrator() {
        isAdministratorAccount = true;
    }
}
