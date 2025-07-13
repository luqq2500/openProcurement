package company;

import company.api.CompanyRegistrator;
import company.application.RegisterCompany;
import company.model.Company;
import company.model.CompanyRegistrationApplication;
import company.model.RegisterCompanyCommand;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class registerCompanyTest {
    @Test
    public void testRegisterCompany() {
        List<Company> companies = new ArrayList<>();
        CompanyRegistrator registrator = new RegisterCompany(()->companies);
        RegisterCompanyCommand command = new RegisterCompanyCommand(new CompanyRegistrationApplication(
                "ndedneww", "ForgeNet", "202380061600", "202380061600", "Cooperative", "MY", LocalDateTime.now(), "Approved", "eejeud993xx", LocalDateTime.now()
        ));
        Company company = registrator.register(command);
        Assert.assertNotNull(company);
        System.out.println(company);
    }
}
