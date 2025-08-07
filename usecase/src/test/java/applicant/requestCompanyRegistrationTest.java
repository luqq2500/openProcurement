package applicant;

import applicant.api.CompanyRegistrationRequestor;

import applicant.dto.RequestCompanyRegistrationRequest;
import company.MockCompanyRegistrationRequestRepository;
import company.spi.CompanyRegistrationRequestRepository;
import email.EmailService;
import email.MockEmailService;
import org.junit.Test;
import token.MockOTPRepository;
import token.MockOTPService;
import token.OTPRepository;
import token.OTPService;

public class requestCompanyRegistrationTest {

    @Test
    public void test() {
        CompanyRegistrationRequestRepository repository = new MockCompanyRegistrationRequestRepository();
        OTPRepository mockOTPRepository = new MockOTPRepository();
        EmailService emailService = new MockEmailService();
        OTPService OTPService = new MockOTPService(mockOTPRepository);
        CompanyRegistrationRequestor requestor = new RequestCompanyRegistration(repository, emailService, OTPService);
        RequestCompanyRegistrationRequest request = new RequestCompanyRegistrationRequest("hakimluqq25@gmail.com");
        requestor.request(request);
    }
}
