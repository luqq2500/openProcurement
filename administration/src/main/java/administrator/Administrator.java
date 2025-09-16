package administrator;

import administration.AdministrationStatus;
import administration.registration.RegistrationAdministration;
import administration.registration.RegistrationApplication;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    public RegistrationAdministration administerRegistration(RegistrationApplication application, AdministrationStatus status, String notes) {
        return new RegistrationAdministration(application.applicationId(), status, administratorId, notes, LocalDateTime.now());
    }
}
