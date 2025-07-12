package administrator.model;

import java.util.Set;

public record AdministratorRoleResponsibilities(String role, Set<String> responsibilities) {
}
