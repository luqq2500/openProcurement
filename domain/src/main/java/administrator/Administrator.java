package administrator;

import java.util.UUID;

public class Administrator {
    private final String administratorId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private AdministratorRoles role;

    public Administrator(String firstName, String lastName, String email, String password, AdministratorRoles role) {
        this.administratorId = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void validateAssignedRole(AdministratorRoles role) {
        if (!this.role.equals(role)) {
            throw new RuntimeException("Invalid administrator role");
        }
    }

    public void updateRole(AdministratorRoles role) {
        if (role.equals(this.role)) {
            throw new RuntimeException("Invalid role update: Administrator role is already set as " + role);
        }
        this.role = role;
    }

    public void updateEmail(String email) {
        if (email.equals(this.email)) {
            throw new RuntimeException("Invalid email update: Administrator email is already set as " + email);
        }
        this.email = email;
    }

    public String getAdministratorId() {
        return administratorId;
    }

    public AdministratorRoles getRole() {
        return role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
