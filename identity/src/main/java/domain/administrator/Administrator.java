package domain.administrator;

import domain.employee.PersonnelDetails;

import java.util.UUID;

public class Administrator {
    private final UUID administratorId;
    private final PersonnelDetails personnelDetails;
    private final AdministratorRole role;

    public Administrator(PersonnelDetails personnelDetails, AdministratorRole role) {
        this.administratorId = UUID.randomUUID();
        this.personnelDetails = personnelDetails;
        this.role = role;
    }
    public boolean validateRole(AdministratorRole role) {
        return this.role == role;
    }

    public UUID getAdministratorId() {
        return administratorId;
    }
}
