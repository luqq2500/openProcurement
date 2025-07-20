package administrator.api;

import administrator.Administrator;

public interface AdministratorRoleResponsibilityValidator {
    public void validate(Administrator administrator, String responsibility);
}
