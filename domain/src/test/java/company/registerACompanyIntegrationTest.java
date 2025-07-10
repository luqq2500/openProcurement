package company;

import company.api.CompanyRegistrationValidator;
import company.api.CompanyRegistrator;
import company.application.*;
import company.exception.CompanyAlreadyExistException;
import company.exception.InvalidCompanyException;
import company.exception.InvalidCountryRegistrationRulesException;
import company.model.RegisterCompanyCommand;
import company.spi.CountryRegistrationRulesRepository;
import company.spi.CompanyRepository;
import company.model.Company;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class registerACompanyIntegrationTest {
    private CompanyRegistrator companyRegistrator;
    @Before
    public void setUp(){
        CompanyRepository companyRepository = new InMemoryCompanyRepository();
        CountryRegistrationRulesRepository countryRegistrationRulesRepository = new InMemoryCountryRegistrationRulesRepository();
        CompanyRegistrationValidator formatValidator = new ValidateRegisterCompanyCommandFormat();
        CompanyRegistrationValidator uniquenessValidator = new ValidateRegisterCompanyIsUnique(companyRepository);
        CompanyRegistrationValidator countryRuleValidator = new ValidateRegistrationCountryRules(countryRegistrationRulesRepository);
        List<CompanyRegistrationValidator> validators = new ArrayList<>();
        validators.add(formatValidator);
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
    public void registerDuplicateCompanyShouldThrowException(){
        RegisterCompanyCommand command = new RegisterCompanyCommand(
                "ForgeNet",
                "202380061600",
                "200877000000",
                "Sole Proprietorship",
                "MY"
        );
        Company company = companyRegistrator.register(command);
        Assert.assertThrows(CompanyAlreadyExistException.class, ()->companyRegistrator.register(command));
    }

    @Test
    public void registerInvalidNameShouldThrowException(){
        RegisterCompanyCommand noName = new RegisterCompanyCommand(
                " ",
                "202380061600",
                "200877000000",
                "Sole Proprietorship",
                "MY"
        );
        Assert.assertThrows(InvalidCompanyException.class, ()->companyRegistrator.register(noName));
    }

    @Test
    public void registerInvalidCountryRuleInformationShouldThrowException(){
        RegisterCompanyCommand invalidRegistrationNumber = new RegisterCompanyCommand(
                "ForgeNet",
                "2008770",
                "200877000000",
                "Sole Proprietorship",
                "MY"
        );
        Assert.assertThrows(InvalidCountryRegistrationRulesException.class, () -> companyRegistrator.register(
                invalidRegistrationNumber
        ));
        RegisterCompanyCommand invalidTaxNumber = new RegisterCompanyCommand(
                "ForgeNet",
                "202380061600",
                "20087700xxx",
                "Sole Proprietorship",
                "US"
        );
        Assert.assertThrows(InvalidCountryRegistrationRulesException.class, () -> companyRegistrator.register(invalidTaxNumber));
        RegisterCompanyCommand invalidCountryCode = new RegisterCompanyCommand(
                "ForgeNet",
                "202380061600",
                "200877000000",
                "Sole Proprietorship",
                "Selangor"
        );
        Assert.assertThrows(InvalidCountryRegistrationRulesException.class, () -> companyRegistrator.register(invalidCountryCode));
    }
}
