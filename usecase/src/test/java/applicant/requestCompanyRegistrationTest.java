package applicant;

import applicant.api.CompanyRegistrationRequestor;
import applicant.dto.RequestCompanyRegistrationCommand;
import org.junit.Test;

public class requestCompanyRegistrationTest {
    @Test
    public void test() {
        CompanyRegistrationRequestor requestor = new RequestCompanyRegistration();
        requestor.request(new RequestCompanyRegistrationCommand("Luqman", "Hakim", "hakimluqq@gmail.com", "0104305988"));
    }
}
