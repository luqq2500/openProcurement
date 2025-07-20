package administrator.spi;

import administrator.AdministratorRoleResponsibilities;

import java.util.List;

public interface AdministratorRoleResponsibilityRepository {
    List<AdministratorRoleResponsibilities> administratorRoleRules();
}
