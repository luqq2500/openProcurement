package company;

import company.api.CompanyRegistrationValidator;
import company.application.ValidateRegisterCompanyCommandFormat;
import company.exception.InvalidCompanyException;
import company.model.RegisterCompanyCommand;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class validateRegisterCompanyCommandFormatTest {
    private CompanyRegistrationValidator validator;

    @Before
    public void setUp() {
        this.validator = new ValidateRegisterCompanyCommandFormat();
    }

    @Test
    public void blankFormatShouldThrowException() {
        CompanyRegistrationValidator validator = new ValidateRegisterCompanyCommandFormat();
        RegisterCompanyCommand command = new RegisterCompanyCommand(
                " ",
                " ",
                " ",
                " ",
                " "
        );
        Assert.assertThrows(InvalidCompanyException.class, () -> {validator.validate(command);});
    }
    @Test
    public void nullFormatShouldThrowException() {
        RegisterCompanyCommand command = new RegisterCompanyCommand(
                "",
                "",
                "",
                "",
                ""
        );
        Assert.assertThrows(InvalidCompanyException.class, () -> {validator.validate(command);});
    }
}
