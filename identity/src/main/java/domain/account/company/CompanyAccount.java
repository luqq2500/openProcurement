package domain.account.company;

import domain.address.Address;
import domain.account.AccountStatus;
import domain.account.employee.EmployeeAccount;
import domain.company.CompanyDetails;

import java.util.List;
import java.util.UUID;

public class CompanyAccount {
    private final UUID registrationId;
    private final UUID id;
    private CompanyDetails companyDetails;
    private List<EmployeeAccount> employeeAccounts;
    private AccountStatus accountStatus;
    public CompanyAccount(UUID registrationId, CompanyDetails companyDetails) {
        this.id = UUID.randomUUID();
        this.registrationId = registrationId;
        this.companyDetails = companyDetails;
        this.accountStatus = AccountStatus.ACTIVE;
    }
    public void addEmployeeAccount(EmployeeAccount employeeAccount){
        employeeAccounts.add(employeeAccount);
    }
    public void removeEmployeeAccount(EmployeeAccount employeeAccount) {
        employeeAccounts.remove(employeeAccount);
    }
    public void changeAddress(Address address) {
        this.companyDetails.changeAddress(address);
    }
}
