package administrator.api;

import administrator.model.Administrator;

public interface AdministratorRoleResponsibilityValidator {
    public void validate(Administrator administrator, String responsibility);
}
