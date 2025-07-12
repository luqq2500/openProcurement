package administrator.spi;

import administrator.model.AdministratorRoleResponsibilities;

import java.util.List;

public interface AdministratorRoleResponsibilityRepository {
    List<AdministratorRoleResponsibilities> administratorRoleRules();
}
