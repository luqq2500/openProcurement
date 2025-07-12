package company;

import company.api.CompanyRegistrationApplicationValidator;
import company.api.CompanyRegistrationApplier;
import company.application.ApplyCompanyRegistration;
import company.application.ValidateRegistrationCountryRules;
import company.exception.CompanyRegistrationApplicationAlreadyExist;
import company.exception.InvalidCompanyRegistrationApplicationCommand;
import company.application.command.ApplyCompanyRegistrationCommand;
import company.model.CompanyRegistrationApplication;
import company.model.CountryRegistrationRules;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class applyRegistrationApplicationTest {
    private CompanyRegistrationApplier applier;

    @Before
    public void setUp() throws Exception {
        var applications = List.of(
                new CompanyRegistrationApplication(UUID.randomUUID().toString(), "Cathedral", "202380071700", "202380071700", "Cooperative", "MY", LocalDateTime.now(), "Pending", null, null)
        );
        var countryRegistrationRules = List.of(
                new CountryRegistrationRules("MY", 12, "\\d{12}", 12 , "\\d{12}",
                        List.of("Sole Proprietorship", "Partnership", "Limited Liability Company (LLC)", "Corporation", "Cooperative"))
        );
        CompanyRegistrationApplicationValidator countryRuleValidator = new ValidateRegistrationCountryRules(()->countryRegistrationRules);
        this.applier = new ApplyCompanyRegistration(()->applications, countryRuleValidator);
    }

    @Test
    public void applyRegistrationApplicationShouldReturn(){
        ApplyCompanyRegistrationCommand command = new ApplyCompanyRegistrationCommand(
                "ForgeNet",
                "202380061600",
                "202380061600",
                "Cooperative",
                "MY"
        );
        CompanyRegistrationApplication application = applier.apply(command);
        Assert.assertNotNull(application);
        System.out.println(application);
    }

    @Test
    public void applyDuplicateRegistrationApplicationShouldThrowException(){
        ApplyCompanyRegistrationCommand command = new ApplyCompanyRegistrationCommand(
                "Cathedral",
                "202380071700",
                "202380071700",
                "Cooperative",
                "MY"
        );
        Assert.assertThrows(CompanyRegistrationApplicationAlreadyExist.class, ()-> applier.apply(command));
    }

    @Test
    public void applyInvalidCompanyRegistrationApplicationCommandShouldThrowException() {
        Assert.assertThrows(InvalidCompanyRegistrationApplicationCommand.class, ()-> new ApplyCompanyRegistrationCommand(
                "",
                "202380061600",
                "202380061600",
                "Cooperative",
                "MY"
        ));
        Assert.assertThrows(InvalidCompanyRegistrationApplicationCommand.class, ()-> new ApplyCompanyRegistrationCommand(
                "ForgeNet",
                "",
                "202380061600",
                "Cooperative",
                "MY"
        ));
        Assert.assertThrows(InvalidCompanyRegistrationApplicationCommand.class, ()-> new ApplyCompanyRegistrationCommand(
                "ForgeNet",
                "202380061600",
                "",
                "Cooperative",
                "MY"
        ));
        Assert.assertThrows(InvalidCompanyRegistrationApplicationCommand.class, ()-> new ApplyCompanyRegistrationCommand(
                "ForgeNet",
                "202380061600",
                "202380061600",
                "",
                "MY"
        ));
        Assert.assertThrows(InvalidCompanyRegistrationApplicationCommand.class, ()-> new ApplyCompanyRegistrationCommand(
                "ForgeNet",
                "202380061600",
                "202380061600",
                "Cooperative",
                ""
        ));
    }
}
