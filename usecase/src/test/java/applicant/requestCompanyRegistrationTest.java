package applicant;

import applicant.api.CompanyRegistrationRequestor;

import applicant.dto.RequestCompanyRegistrationRequest;
import applicant.dto.RequestCompanyRegistrationResponse;
import company.MockCompanyRegistrationRequestRepository;
import company.spi.CompanyRegistrationRequestRepository;
import email.EmailService;
import email.MockEmailService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import otp.MockOTPRepository;
import otp.MockOTPService;
import otp.OTPRepository;
import otp.OTPService;

public class requestCompanyRegistrationTest {
    private OTPRepository mockOTPRepository;
    private CompanyRegistrationRequestor requestor;
    @Before
    public void setUp(){
        CompanyRegistrationRequestRepository repository = new MockCompanyRegistrationRequestRepository();
        mockOTPRepository = new MockOTPRepository();
        EmailService emailService = new MockEmailService();
        OTPService OTPService = new MockOTPService(mockOTPRepository);
        requestor = new RequestCompanyRegistration(repository, emailService, OTPService);
    }

    @Test
    public void requestOnce_OTPShouldBeEnabled(){
        RequestCompanyRegistrationRequest request = new RequestCompanyRegistrationRequest("hakimluqq25@gmail.com");
        RequestCompanyRegistrationResponse response = requestor.request(request);
        System.out.println(response);
        Assert.assertTrue(mockOTPRepository.passwords().getFirst().isEnabled());
    }

    @Test
    public void twoSameRequestFrom_shouldNotThrowException(){
        RequestCompanyRegistrationRequest request = new RequestCompanyRegistrationRequest("hakimluqq25@gmail.com");
        RequestCompanyRegistrationResponse response1 = requestor.request(request);
        RequestCompanyRegistrationResponse response2 = requestor.request(request);
        System.out.println(response1);
        System.out.println(response2);
    }

    @Test
    public void twoDifferentRequestFrom_shouldNotThrowException(){
        RequestCompanyRegistrationRequest request1 = new RequestCompanyRegistrationRequest("hakimluqq25@gmail.com");
        RequestCompanyRegistrationRequest request2 = new RequestCompanyRegistrationRequest("hakimluqq58@gmail.com");
        RequestCompanyRegistrationResponse response1 = requestor.request(request1);
        RequestCompanyRegistrationResponse response2 = requestor.request(request2);
        System.out.println(response1);
        System.out.println(response2);
    }

    @Test
    public void twoDifferentRequestFrom_bothRequestsShouldBeEnabled(){
        RequestCompanyRegistrationRequest request1 = new RequestCompanyRegistrationRequest("hakimluqq25@gmail.com");
        RequestCompanyRegistrationRequest request2 = new RequestCompanyRegistrationRequest("hakimluqq58@gmail.com");
        requestor.request(request1);
        requestor.request(request2);
        Assert.assertTrue(mockOTPRepository.passwords().getFirst().isEnabled());
        Assert.assertTrue(mockOTPRepository.passwords().getLast().isEnabled());
    }
}
