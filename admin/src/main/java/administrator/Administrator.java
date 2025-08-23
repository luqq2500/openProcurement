package administrator;

import java.util.UUID;

public class Administrator {
    private final UUID administratorId;
    private final AdministratorRole role;
    public Administrator(AdministratorRole role) {
        this.administratorId = UUID.randomUUID();
        this.role = role;
    }
    public boolean validateRole(AdministratorRole role) {return this.role == role;}
    public UUID getAdministratorId() {return administratorId;}
}
