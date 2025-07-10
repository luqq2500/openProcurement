package company;

import company.api.CompanyRegistrationValidator;
import company.application.ValidateRegistrationCountryRules;
import company.exception.InvalidCountryRegistrationRulesException;
import company.model.RegisterCompanyCommand;
import company.spi.CountryRegistrationRulesRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class validateCountryRegistrationRuleTest {
    private CompanyRegistrationValidator validator;

    @Before
    public void setUp() {
        CountryRegistrationRulesRepository rulesRepository = new InMemoryCountryRegistrationRulesRepository();
        this.validator = new ValidateRegistrationCountryRules(rulesRepository);
    }

    @Test
    public void validInformationShouldNotThrowException(){
        RegisterCompanyCommand command = new RegisterCompanyCommand(
                "ForgeNet",
                "202380061600",
                "202380061600",
                "Cooperative",
                "MY"
        );
        validator.validate(command);
    }

    @Test
    public void invalidRegistrationNumberLengthShouldThrowException(){
        RegisterCompanyCommand command = new RegisterCompanyCommand(
                "ForgeNet",
                "2023800616",
                "202380061600",
                "Cooperative",
                "MY"
        );
        Assert.assertThrows(InvalidCountryRegistrationRulesException.class, () -> validator.validate(command));
    }

    @Test
    public void invalidRegistrationNumberPatternShouldThrowException(){
        RegisterCompanyCommand command = new RegisterCompanyCommand(
                "ForgeNet",
                "202380061@xx",
                "202380061600",
                "Cooperative",
                "MY"
        );
        Assert.assertThrows(InvalidCountryRegistrationRulesException.class, () -> validator.validate(command));
    }

    @Test
    public void invalidTaxNumberLengthShouldThrowException(){
        RegisterCompanyCommand command = new RegisterCompanyCommand(
                "ForgeNet",
                "202380061600",
                "2023800",
                "Cooperative",
                "MY"
        );
        Assert.assertThrows(InvalidCountryRegistrationRulesException.class, () -> validator.validate(command));
    }

    @Test
    public void invalidTaxNumberPatternShouldThrowException(){
        RegisterCompanyCommand command = new RegisterCompanyCommand(
                "ForgeNet",
                "202380061600",
                "202380061xxx",
                "Cooperative",
                "MY"
        );
        Assert.assertThrows(InvalidCountryRegistrationRulesException.class, () -> validator.validate(command));
    }

    @Test
    public void invalidMYBusinessStructureShouldThrowException(){
        RegisterCompanyCommand command = new RegisterCompanyCommand(
                "ForgeNet",
                "202380061600",
                "202380061600",
                "Gmbh",
                "MY"
        );
        Assert.assertThrows(InvalidCountryRegistrationRulesException.class, () -> validator.validate(command));
    }

    @Test
    public void invalidCountryShouldThrowException(){
        RegisterCompanyCommand command = new RegisterCompanyCommand(
                "ForgeNet",
                "202380061600",
                "202380061600",
                "Cooperative",
                "Selangor"
        );
        Assert.assertThrows(InvalidCountryRegistrationRulesException.class, () -> validator.validate(command));
    }
}
