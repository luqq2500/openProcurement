package company;

import company.api.CompanyRegistrationValidator;
import company.api.CompanyRegistrator;
import company.application.*;
import company.exception.CompanyAlreadyExistException;
import company.exception.InvalidRegisterCompanyCommand;
import company.model.RegisterCompanyCommand;
import company.spi.CountryRegistrationRulesRepository;
import company.spi.CompanyRepository;
import company.model.Company;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class registerACompanyTest {
    private CompanyRegistrator registrator;
    @Before
    public void setUp(){
        CompanyRepository companyRepository = new InMemoryCompanyRepository();
        CountryRegistrationRulesRepository countryRegistrationRulesRepository = new InMemoryCountryRegistrationRulesRepository();
        CompanyRegistrationValidator countryRuleValidator = new ValidateRegistrationCountryRules(countryRegistrationRulesRepository);
        this.registrator = new RegisterACompany(companyRepository, countryRuleValidator);
    }

    @Test
    public void registerACompanyShouldReturnCompany(){
        RegisterCompanyCommand command = new RegisterCompanyCommand(
                "ForgeNet",
                "202380061600",
                "200877000000",
                "Sole Proprietorship",
                "MY"
        );
        Company company = registrator.register(command);
        Assert.assertNotNull(company);
        System.out.println("Id: " + company.getId());
        System.out.println("Name: " + company.getName());
    }

    @Test
    public void registerInvalidCommandShouldThrowException(){
        Assert.assertThrows(InvalidRegisterCompanyCommand.class,() -> {
            new RegisterCompanyCommand(
                    " ",
                    " ",
                    " ",
                    " ",
                    " "
            );
        });
    }

    @Test
    public void registerDuplicateCompanyShouldThrowException() {
        RegisterCompanyCommand command = new RegisterCompanyCommand(
                "ForgeNet",
                "202380061600",
                "202380061600",
                "Cooperative",
                "MY"
        );
        registrator.register(command);
        Assert.assertThrows(CompanyAlreadyExistException.class, ()->registrator.register(command));
    }

    @Test
    public void registerAUniqueCompanyShouldNotThrowException(){
        RegisterCompanyCommand command1 = new RegisterCompanyCommand(
                "ForgeNet",
                "202380061600",
                "202380061600",
                "Cooperative",
                "MY"
        );
        registrator.register(command1);
        RegisterCompanyCommand command2 = new RegisterCompanyCommand(
                "ForgeNet",
                "202080061600",
                "202080061600",
                "Cooperative",
                "MY"
        );
        registrator.register(command2);
    }
}
