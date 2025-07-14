package validator;

import address.model.AddressCommand;
import company.command.ApplyCompanyRegistrationCommand;
import validator.api.CompanyRegistrationApplicationValidator;
import validator.application.ValidateCompanyRegistrationCountryRules;
import company.exception.InvalidCountryRegistrationRulesException;
import company.model.CompanyRegistrationCountryRule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class validateCountryRegistrationRuleTest {
    private CompanyRegistrationApplicationValidator validator;

    @Before
    public void setUp() {
        var rules = List.of(
                new CompanyRegistrationCountryRule("Malaysia", 12, "\\d{12}", 12 , "\\d{12}",
                        List.of("Sole Proprietorship", "Partnership", "Limited Liability Company (LLC)", "Corporation", "Cooperative"))
        );
        this.validator = new ValidateCompanyRegistrationCountryRules(()->rules);
    }

    @Test
    public void validInformationShouldNotThrowException(){
        ApplyCompanyRegistrationCommand command = new ApplyCompanyRegistrationCommand(
                "ForgeNet",
                "202380061600",
                "202380061600",
                "Cooperative",
                new AddressCommand(
                        "1, Jalan Dahlia 8/2, Taman Dahlia",
                        "Bandar Baru Salak Tinggi",
                        null,
                        "Petaling Jaya",
                        "Selangor",
                        "46100",
                        "Malaysia"
                )
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
                new AddressCommand(
                        "1, Jalan Dahlia 8/2, Taman Dahlia",
                        "Bandar Baru Salak Tinggi",
                        null,
                        "Petaling Jaya",
                        "Selangor",
                        "46100",
                        "Malaysia"
                )
        );
        InvalidCountryRegistrationRulesException exception = Assert.assertThrows(InvalidCountryRegistrationRulesException.class, () -> validator.validate(command));
        System.out.println(exception.getMessage());
    }

    @Test
    public void invalidRegistrationNumberPatternShouldThrowException(){
        ApplyCompanyRegistrationCommand command = new ApplyCompanyRegistrationCommand(
                "ForgeNet",
                "202380061@xx",
                "202380061600",
                "Cooperative",
                new AddressCommand(
                        "1, Jalan Dahlia 8/2, Taman Dahlia",
                        "Bandar Baru Salak Tinggi",
                        null,
                        "Petaling Jaya",
                        "Selangor",
                        "46100",
                        "Malaysia"
                )
        );
        InvalidCountryRegistrationRulesException exception = Assert.assertThrows(InvalidCountryRegistrationRulesException.class, () -> validator.validate(command));
        System.out.println(exception.getMessage());
    }

    @Test
    public void invalidTaxNumberLengthShouldThrowException(){
        ApplyCompanyRegistrationCommand command = new ApplyCompanyRegistrationCommand(
                "ForgeNet",
                "202380061600",
                "2023800",
                "Cooperative",
                new AddressCommand(
                        "1, Jalan Dahlia 8/2, Taman Dahlia",
                        "Bandar Baru Salak Tinggi",
                        null,
                        "Petaling Jaya",
                        "Selangor",
                        "46100",
                        "Malaysia"
                )
        );
        InvalidCountryRegistrationRulesException exception = Assert.assertThrows(InvalidCountryRegistrationRulesException.class, () -> validator.validate(command));
        System.out.println(exception.getMessage());
    }

    @Test
    public void invalidTaxNumberPatternShouldThrowException(){
        ApplyCompanyRegistrationCommand command = new ApplyCompanyRegistrationCommand(
                "ForgeNet",
                "202380061600",
                "202380061xxx",
                "Cooperative",
                new AddressCommand(
                        "1, Jalan Dahlia 8/2, Taman Dahlia",
                        "Bandar Baru Salak Tinggi",
                        null,
                        "Petaling Jaya",
                        "Selangor",
                        "46100",
                        "Malaysia"
                )
        );
        InvalidCountryRegistrationRulesException exception = Assert.assertThrows(InvalidCountryRegistrationRulesException.class, () -> validator.validate(command));
        System.out.println(exception.getMessage());
    }

    @Test
    public void invalidMYBusinessStructureShouldThrowException(){
        ApplyCompanyRegistrationCommand command = new ApplyCompanyRegistrationCommand(
                "ForgeNet",
                "202380061600",
                "202380061600",
                "Gmbh",
                new AddressCommand(
                        "1, Jalan Dahlia 8/2, Taman Dahlia",
                        "Bandar Baru Salak Tinggi",
                        null,
                        "Petaling Jaya",
                        "Selangor",
                        "46100",
                        "Malaysia"
                )
        );
        InvalidCountryRegistrationRulesException exception = Assert.assertThrows(InvalidCountryRegistrationRulesException.class, () -> validator.validate(command));
        System.out.println(exception.getMessage());
    }

    @Test
    public void invalidCountryShouldThrowException(){
        ApplyCompanyRegistrationCommand command = new ApplyCompanyRegistrationCommand(
                "ForgeNet",
                "202380061600",
                "202380061600",
                "Cooperative",
                new AddressCommand(
                        "1, Jalan Dahlia 8/2, Taman Dahlia",
                        "Bandar Baru Salak Tinggi",
                        null,
                        "Petaling Jaya",
                        "Selangor",
                        "46100",
                        "Selangor"
                )
        );
        InvalidCountryRegistrationRulesException exception = Assert.assertThrows(InvalidCountryRegistrationRulesException.class, () -> validator.validate(command));
        System.out.println(exception.getMessage());
    }
}
