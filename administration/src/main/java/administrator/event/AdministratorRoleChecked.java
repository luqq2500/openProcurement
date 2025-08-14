package administrator.event;

import administrator.AdministratorRole;
import domain.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public class AdministratorRoleChecked implements DomainEvent {
    private final UUID administratorId;
    private final AdministratorRole actualAdministratorRole;
    private final AdministratorRole administratorRoleToCheck;
    private final boolean roleValid;
    private final Instant checkedAt;
    public AdministratorRoleChecked(UUID administratorId, AdministratorRole actualAdministratorRole, AdministratorRole administratorRoleToCheck, boolean roleValid) {
        this.administratorId = administratorId;
        this.actualAdministratorRole = actualAdministratorRole;
        this.administratorRoleToCheck = administratorRoleToCheck;
        this.roleValid = roleValid;
        this.checkedAt = Instant.now();
    }
    @Override
    public Instant getTimestamp() {
        return checkedAt;
    }
}
