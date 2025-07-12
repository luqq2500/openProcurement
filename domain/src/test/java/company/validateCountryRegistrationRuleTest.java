package company;

import company.api.CompanyRegistrationApplicationValidator;
import company.application.ValidateRegistrationCountryRules;
import company.exception.InvalidCountryRegistrationRulesException;
import company.model.ApplyCompanyRegistrationCommand;
import company.model.CountryRegistrationRules;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class validateCountryRegistrationRuleTest {
    private CompanyRegistrationApplicationValidator validator;

    @Before
    public void setUp() {
        var rules = List.of(
                new CountryRegistrationRules("MY", 12, "\\d{12}", 12 , "\\d{12}",
                        List.of("Sole Proprietorship", "Partnership", "Limited Liability Company (LLC)", "Corporation", "Cooperative"))
        );
        this.validator = new ValidateRegistrationCountryRules(()->rules);
    }

    @Test
    public void validInformationShouldNotThrowException(){
        ApplyCompanyRegistrationCommand command = new ApplyCompanyRegistrationCommand(
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
        ApplyCompanyRegistrationCommand command = new ApplyCompanyRegistrationCommand(
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
        ApplyCompanyRegistrationCommand command = new ApplyCompanyRegistrationCommand(
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
        ApplyCompanyRegistrationCommand command = new ApplyCompanyRegistrationCommand(
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
        ApplyCompanyRegistrationCommand command = new ApplyCompanyRegistrationCommand(
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
        ApplyCompanyRegistrationCommand command = new ApplyCompanyRegistrationCommand(
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
        ApplyCompanyRegistrationCommand command = new ApplyCompanyRegistrationCommand(
                "ForgeNet",
                "202380061600",
                "202380061600",
                "Cooperative",
                "Selangor"
        );
        Assert.assertThrows(InvalidCountryRegistrationRulesException.class, () -> validator.validate(command));
    }
}
