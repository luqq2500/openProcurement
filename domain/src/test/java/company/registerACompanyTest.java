package company;

import company.api.CompanyRegistrationValidator;
import company.api.CompanyRegistrator;
import company.application.*;
import company.exception.CompanyAlreadyExistException;
import company.model.CountryRegistrationRules;
import company.model.RegisterCompanyCommand;
import company.model.Company;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.stream.Stream;


public class registerACompanyTest {
    private CompanyRegistrationValidator validator;
    @Before
    public void setUp(){
        var rules = Stream.of(
                new CountryRegistrationRules("US", 9, "\\d{9}", 9, "\\d{9}",
                        List.of("Sole Proprietorship", "Partnership", "Limited Liability Company (LLC)", "Corporation", "Cooperative")),
                new CountryRegistrationRules("MY", 12, "\\d{12}", 12 , "\\d{12}",
                        List.of("Sole Proprietorship", "Partnership", "Limited Liability Company (LLC)", "Corporation", "Cooperative"))
        );
        this.validator = new ValidateRegistrationCountryRules(()->rules);
    }

    @Test
    public void registerANewCompanyShouldReturnCompany(){
        var companies = Stream.of(
                new Company("ForgeNet", "202380061600", "202380061600", "Cooperative", "MY")
        );
        CompanyRegistrator registrator = new RegisterACompany(()->companies, validator);
        RegisterCompanyCommand command = new RegisterCompanyCommand(
                "Cathedral Technologies",
                "202380061800",
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
    public void registerDuplicateCompanyShouldThrowException() {
        var companies = Stream.of(
                new Company("ForgeNet", "202380061600", "202380061600", "Cooperative", "MY")
        );
        CompanyRegistrator registrator = new RegisterACompany(()->companies, validator);
        RegisterCompanyCommand command = new RegisterCompanyCommand(
                "ForgeNet",
                "202380061600",
                "202380061600",
                "Cooperative",
                "MY"
        );
        Assert.assertThrows(CompanyAlreadyExistException.class, ()->registrator.register(command));
    }
}
