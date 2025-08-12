package administrator;

public enum AdministratorRole {
    SYSTEM_ADMINISTRATOR,
    IDENTITY_AND_ACCESS_ADMINISTRATOR,
    PROCUREMENT_ADMINISTRATOR;
    public void checkAssignedRole(AdministratorRole assignedRole){
        if (!this.equals(assignedRole)){
            throw new InvalidAdministratorRole("Administrator role is not assigned to " + assignedRole.toString());
        }
    }
}
