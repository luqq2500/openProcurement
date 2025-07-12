package company;

import company.api.CompanyRegistrator;
import company.application.RegisterCompany;
import company.model.Company;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class registerCompanyTest {
    @Test
    public void testRegisterCompany() {
        List<Company> companies = new ArrayList<>();
        CompanyRegistrator registrator = new RegisterCompany(()->companies);
        Company company = registrator.register("ForgeNet", "202380061600", "202390061600", "Cooperative", "MY");
        Assert.assertNotNull(company);
    }
}
