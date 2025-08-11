package administrator;

import administrator.exception.AdministratorRoleUnchangedException;
import company.CompanyRegistration;
import company.CompanyRegistrationStatus;
import company.exception.InvalidCompanyRegistrationStatus;

import java.util.UUID;

public class Administrator {
    private final UUID administratorId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private AdministratorRoles role;

    public Administrator(String firstName, String lastName, String email, String password, AdministratorRoles role) {
        this.administratorId = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void updateRole(AdministratorRoles role) {
        if (role.equals(this.role)) {
            throw new AdministratorRoleUnchangedException("Invalid role update: Administrator role is already set as " + role);
        }
        this.role = role;
    }

    public void validateRole(AdministratorRoles role) {
        this.role.validateAssignedRole(role);
    }

    public UUID getAdministratorId() {
        return administratorId;
    }

    public void changeAdministratorName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public String  getPassword() {
        return password;
    }
}
