package company;

import company.api.CompanyRegistrator;
import company.spi.CompanyRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class registerACompanyFunctionalTest {
    private CompanyRegistrator companyRegistrator;

    @Before
    public void setUp(){
        CompanyRepository companyRepository = new InMemoryCompanyRepository();
        this.companyRegistrator = new RegisterACompany(companyRepository);
    }

    @Test
    public void registerACompanyShouldReturnCompany(){

        Company company = companyRegistrator.register(
                "ForgeNet",
                "Sole Proprietorship",
                "202380061600",
                "200877000000");

        Assert.assertNotNull(company);
        System.out.println("Id: " + company.getId());
        System.out.println("Name: " + company.getCompanyName());
    }

    @Test
    public void registerDuplicateCompanyShouldThrowException(){
        Company company = companyRegistrator.register(
                "ForgeNet",
                "Sole Proprietorship",
                "202380061600",
                "200877000000");

        Assert.assertThrows(RuntimeException.class, () -> companyRegistrator.register(
                "ForgeNet",
                "Sole Proprietorship",
                "202380061600",
                "200877000000"
        ));
    }

    @Test
    public void registerEmptyInformationCompanyShouldThrowException(){
        Assert.assertThrows(RuntimeException.class, () -> companyRegistrator.register(
                "",
                "",
                " ",
                " "));
        Assert.assertThrows(RuntimeException.class, () -> companyRegistrator.register(
                "ForgeNet",
                "no structure",
                "202380061600",
                "200877000000"
        ));
        Assert.assertThrows(RuntimeException.class, () -> companyRegistrator.register(
                "ForgeNet",
                "Sole Proprietorship",
                "2023800xxxxx",
                "200877000000"
        ));
        Assert.assertThrows(RuntimeException.class, () -> companyRegistrator.register(
                "ForgeNet",
                "no structure",
                "202380061600",
                "2008770xxxx"
        ));

    }
}
