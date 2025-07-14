package administrator.model;

import administrator.exception.InvalidAdministratorRoleRuleException;
import administrator.exception.NotAuthorizedAdministratorRoleResponsibility;

import java.util.Set;

public record AdministratorRoleResponsibilities(String role, Set<String> responsibilities) {

    public void validate(String role, String responsibility){
        if(role == null || role.isEmpty()) throw new InvalidAdministratorRoleRuleException("Role cannot be null or empty");
        if(responsibility == null || responsibility.isEmpty()) throw new InvalidAdministratorRoleRuleException("Responsibilities cannot be null or empty");
        validateRole(role);
        validateResponsibility(responsibility);
    }

    private void validateRole(String checkRole){
        if (!checkRole.equals(role())){
            throw new NotAuthorizedAdministratorRoleResponsibility(checkRole + " is not authorized role for responsibility " + responsibilities() + ".");
        }
    }

    private void validateResponsibility(String responsibility){
        if (!responsibilities().contains(responsibility)){
            throw new NotAuthorizedAdministratorRoleResponsibility(responsibility + " is not authorized responsibility for role " + role + ".");
        }
    }


}
