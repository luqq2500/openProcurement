package validator;

import validator.api.AdministratorRoleResponsibilityValidator;
import validator.application.ValidateAdministratorRoleResponsibility;
import validator.exception.NotAuthorizedAdministratorRoleResponsibility;
import administrator.model.Administrator;
import administrator.model.AdministratorRoleResponsibilities;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Set;

public class ValidateAdministratorRoleResponsibilityTest {
    @Test
    public void authorizedAdministratorRoleResponsibilityShouldNotThrowException() {
        var roleRules = List.of(
                new AdministratorRoleResponsibilities("Adjudicator", Set.of("processCompanyRegistrationApplication", "approveCompanyRegistrationApplication", "rejectCompanyRegistrationApplication"))
        );
        Administrator administrator = new Administrator("haha", "haha", "haha", "haha", "Adjudicator");
        AdministratorRoleResponsibilityValidator validator = new ValidateAdministratorRoleResponsibility(()->roleRules);
        validator.validate(administrator, "processCompanyRegistrationApplication");
    }

    @Test
    public void notAuthorizedAdministratorRoleResponsibilityShouldThrowException() {
        var roleRules = List.of(
                new AdministratorRoleResponsibilities("Adjudicator", Set.of("processCompanyRegistrationApplication", "approveCompanyRegistrationApplication", "rejectCompanyRegistrationApplication"))
        );
        Administrator administrator = new Administrator("haha", "haha", "haha", "haha", "Adjudicator");
        AdministratorRoleResponsibilityValidator validator = new ValidateAdministratorRoleResponsibility(()->roleRules);
        NotAuthorizedAdministratorRoleResponsibility exception = Assert.assertThrows(NotAuthorizedAdministratorRoleResponsibility.class, ()->{validator.validate(administrator, "authorizeApplication");});
        System.out.println(exception.getMessage());
    }
}
