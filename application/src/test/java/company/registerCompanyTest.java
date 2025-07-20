package company;

import address.AddressCommand;
import company.api.CompanyRegistrator;
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
        AddressCommand addressCommand = new AddressCommand(
                "1, Jalan Dahlia 8/2, Taman Dahlia",
                "Bandar Baru Salak Tinggi",
                null,
                "Petaling Jaya",
                "Selangor",
                "46100",
                "Malaysia"
        );
        CompanyRegistrationApplication command = new CompanyRegistrationApplication(
                "xxi3e3jj",
                "ndedneww",
                "ForgeNet",
                "202380061600",
                "202380061600",
                addressCommand,
                LocalDateTime.now(),
                "Approved",
                "cddccd9292hh",
                LocalDateTime.now()
        );
        Company company = registrator.register(command);
        Assert.assertNotNull(company);
        System.out.println(company);
    }
}
