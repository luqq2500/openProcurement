package administrator;

public enum AdministratorRoles {
    PROCUREMENT_ADMINISTRATOR,
    SYSTEM_ADMINISTRATOR;

    public void validateAssignedRole(AdministratorRoles role) {
        if (!(this == role)){
            throw new RuntimeException("Invalid role.");
        };
    }
}
