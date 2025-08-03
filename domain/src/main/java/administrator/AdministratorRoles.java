package administrator;

import administrator.exception.AdministratorRoleInvalidException;

public enum AdministratorRoles {
    PROCUREMENT_ADMINISTRATOR,
    SYSTEM_ADMINISTRATOR;

    public void validateAssignedRole(AdministratorRoles role) {
        if (!(this == role)){
            throw new AdministratorRoleInvalidException("Invalid role.");
        };
    }
}
