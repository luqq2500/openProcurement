package company;

import company.api.CompanyRegistrationValidator;
import company.api.CompanyRegistrator;
import company.application.*;
import company.exception.InvalidRegisterCompanyCommand;
import company.model.RegisterCompanyCommand;
import company.spi.CountryRegistrationRulesRepository;
import company.spi.CompanyRepository;
import company.model.Company;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class registerACompanyTest {
    private CompanyRegistrator companyRegistrator;
    @Before
    public void setUp(){
        CompanyRepository companyRepository = new InMemoryCompanyRepository();
        CountryRegistrationRulesRepository countryRegistrationRulesRepository = new InMemoryCountryRegistrationRulesRepository();
        CompanyRegistrationValidator uniquenessValidator = new ValidateRegisterCompanyIsUnique(companyRepository);
        CompanyRegistrationValidator countryRuleValidator = new ValidateRegistrationCountryRules(countryRegistrationRulesRepository);
        List<CompanyRegistrationValidator> validators = new ArrayList<>();
        validators.add(countryRuleValidator);
        validators.add(uniquenessValidator);
        this.companyRegistrator = new RegisterACompany(companyRepository, validators);
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
        Company company = companyRegistrator.register(command);
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
}
