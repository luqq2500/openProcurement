package identities.company;

import identities.address.Address;
import identities.account.AccountStatus;
import identities.employee.Employee;

import java.util.List;
import java.util.UUID;

public class Company {
    private final UUID registrationId;
    private final UUID id;
    private CompanyDetails companyDetails;
    private List<Employee> employees;
    private AccountStatus accountStatus;
    public Company(UUID registrationId, CompanyDetails companyDetails) {
        this.id = UUID.randomUUID();
        this.registrationId = registrationId;
        this.companyDetails = companyDetails;
        this.accountStatus = AccountStatus.ACTIVE;
    }
    public void addEmployeeAccount(Employee employee){
        employees.add(employee);
    }
    public void removeEmployeeAccount(Employee employee) {
        employees.remove(employee);
    }
    public void changeAddress(Address address) {
        this.companyDetails.changeAddress(address);
    }
}
