package administrator;

import administrator.exception.InvalidAdministratorRoleRuleException;
import administrator.api.AdministratorRoleResponsibilityValidator;
import administrator.spi.AdministratorRoleResponsibilityRepository;

public class ValidateAdministratorRoleResponsibility implements AdministratorRoleResponsibilityValidator {
    private final AdministratorRoleResponsibilityRepository repository;

    public ValidateAdministratorRoleResponsibility(AdministratorRoleResponsibilityRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validate(Administrator administrator, String responsibility) {
        AdministratorRoleResponsibilities roleResponsibilities = getRoleResponsibilities(administrator);
        roleResponsibilities.validate(administrator.getRole(), responsibility);
    }

    private AdministratorRoleResponsibilities getRoleResponsibilities(Administrator administrator) {
        return repository.administratorRoleRules().stream()
                .filter(administratorRoleResponsibilities -> administratorRoleResponsibilities.role().equals(administrator.getRole()))
                .findFirst().orElseThrow(() -> new InvalidAdministratorRoleRuleException("Administrator role '" + administrator.getRole() + "' is unregistered role."));
    }
}
