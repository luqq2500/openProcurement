package administrator.application;

import administrator.api.AdministratorRoleResponsibilityValidator;
import administrator.exception.NotAuthorizedAdministratorRoleResponsibility;
import administrator.model.Administrator;
import administrator.spi.AdministratorRoleResponsibilityRepository;

public class ValidateAdministratorRoleResponsibility implements AdministratorRoleResponsibilityValidator {
    private final AdministratorRoleResponsibilityRepository repository;

    public ValidateAdministratorRoleResponsibility(AdministratorRoleResponsibilityRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validate(Administrator administrator, String responsibility) {
        repository.administratorRoleRules().stream()
                .filter(roleRules -> roleRules.role().equals(administrator.getRole()))
                .filter(roleRules -> roleRules.responsibilities().contains(responsibility))
                .findAny()
                .orElseThrow(()-> new NotAuthorizedAdministratorRoleResponsibility("Role " + administrator.getRole() + " does not have authorization for responsibility " + responsibility));
    }
}
