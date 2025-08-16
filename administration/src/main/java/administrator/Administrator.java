package administrator;

import java.util.UUID;

public class Administrator {
    public final UUID administratorId;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final AdministratorRole role;
    public Administrator(String firstName, String lastName, String email, String password, AdministratorRole role) {
        this.administratorId = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    public void validateRole(AdministratorRole assignedRole) {
        this.role.checkAssignedRole(assignedRole);
    }
    public UUID getAdministratorId() {
        return administratorId;
    }

}
